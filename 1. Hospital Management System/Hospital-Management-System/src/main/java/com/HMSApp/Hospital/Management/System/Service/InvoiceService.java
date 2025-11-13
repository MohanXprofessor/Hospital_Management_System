package com.HMSApp.Hospital.Management.System.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.HMSApp.Hospital.Management.System.Entity.Appointment;
import com.HMSApp.Hospital.Management.System.Entity.Invoice;
import com.HMSApp.Hospital.Management.System.Entity.InvoiceLineItem;
import com.HMSApp.Hospital.Management.System.Entity.Patient;
import com.HMSApp.Hospital.Management.System.Enum.InvoiceStatus;
import com.HMSApp.Hospital.Management.System.Repository.AppointmentRepository;
import com.HMSApp.Hospital.Management.System.Repository.InvoiceRepository;
import com.HMSApp.Hospital.Management.System.Repository.PatientRepository;

import jakarta.transaction.Transactional;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepo;
    private final PatientRepository patientRepo;
    private final AppointmentRepository appointmentRepo;

    // very simple invoice sequence for demo (replace with DB sequence in prod)
    private static final AtomicInteger SEQ = new AtomicInteger(1);

    public InvoiceService(InvoiceRepository invoiceRepo,
                          PatientRepository patientRepo,
                          AppointmentRepository appointmentRepo) {
        this.invoiceRepo = invoiceRepo;
        this.patientRepo = patientRepo;
        this.appointmentRepo = appointmentRepo;
    }

    private String generateInvoiceNo() {
        String date = java.time.LocalDate.now().toString().replace("-", "");
        return "INV-" + date + "-" + String.format("%04d", SEQ.getAndIncrement());
    }

    @Transactional
    public Invoice createInvoice(Long appointmentId, Long patientId, java.util.List<InvoiceLineItem> items, BigDecimal discountTotal) {
        Patient p = patientRepo.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        Appointment appt = appointmentRepo.findById(appointmentId).orElse(null);

        Invoice inv = new Invoice();
        inv.setInvoiceNo(generateInvoiceNo());
        inv.setPatient(p);
        inv.setAppointment(appt);
        inv.setCreatedAt(LocalDateTime.now());
        inv.setStatus(InvoiceStatus.ISSUED);
        inv.setDiscountTotal(discountTotal == null ? BigDecimal.ZERO : discountTotal);

        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal taxTotal = BigDecimal.ZERO;

        if (items != null) {
            for (InvoiceLineItem it : items) {
                BigDecimal lineNet = it.getUnitPrice().multiply(BigDecimal.valueOf(it.getQuantity()));
                BigDecimal lineTax = BigDecimal.ZERO;
                if (it.getTaxPercent() != null) {
                    lineTax = lineNet.multiply(it.getTaxPercent()).divide(BigDecimal.valueOf(100));
                }
                BigDecimal lineTotal = lineNet.add(lineTax);
                it.setLineTotal(lineTotal);
                inv.addLineItem(it);

                subtotal = subtotal.add(lineNet);
                taxTotal = taxTotal.add(lineTax);
            }
        }

        inv.setSubtotal(subtotal);
        inv.setTaxTotal(taxTotal);
        BigDecimal total = subtotal.add(taxTotal).subtract(inv.getDiscountTotal());
        inv.setTotalAmount(total);
        inv.setPaidAmount(BigDecimal.ZERO);

        return invoiceRepo.save(inv);
    }

    // record a payment (very simple)
    @Transactional
    public Invoice recordPayment(Long invoiceId, BigDecimal amount) {
        Invoice inv = invoiceRepo.findById(invoiceId).orElseThrow(() -> new IllegalArgumentException("Invoice not found"));
        BigDecimal paid = inv.getPaidAmount().add(amount);
        inv.setPaidAmount(paid);

        if (paid.compareTo(inv.getTotalAmount()) >= 0) inv.setStatus(InvoiceStatus.PAID);
        else inv.setStatus(InvoiceStatus.PARTIALLY_PAID);

        return invoiceRepo.save(inv);
    }
}
