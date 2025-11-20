package com.HMSApp.Hospital.Management.System.Controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HMSApp.Hospital.Management.System.Entity.Invoice;
import com.HMSApp.Hospital.Management.System.Entity.InvoiceLineItem;
import com.HMSApp.Hospital.Management.System.Service.InvoiceService;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final InvoiceService service;
    public InvoiceController(InvoiceService service) { this.service = service; }

    @PostMapping
    public Invoice create(@RequestBody CreateInvoiceRequest req) {
        return service.createInvoice(req.appointmentId, req.patientId, req.lineItems, req.discountTotal);
    }

    @PostMapping("/{id}/payments")
    public Invoice pay(@PathVariable Long id, @RequestBody PaymentRequest req) {
        return service.recordPayment(id, req.amount);
    }

    public static class CreateInvoiceRequest {
        public Long appointmentId;
        public Long patientId;
        public List<InvoiceLineItem> lineItems;
        public BigDecimal discountTotal;
    }

    public static class PaymentRequest {
        public BigDecimal amount;
        public String method; // CASH, CARD etc - not used in service yet
        public String transactionRef;
    }
}
