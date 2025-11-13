package com.HMSApp.Hospital.Management.System.Service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.HMSApp.Hospital.Management.System.Entity.Appointment;
import com.HMSApp.Hospital.Management.System.Entity.Doctor;
import com.HMSApp.Hospital.Management.System.Entity.Patient;
import com.HMSApp.Hospital.Management.System.Entity.Prescription;
import com.HMSApp.Hospital.Management.System.Entity.PrescriptionItem;
import com.HMSApp.Hospital.Management.System.Repository.AppointmentRepository;
import com.HMSApp.Hospital.Management.System.Repository.DoctorRepository;
import com.HMSApp.Hospital.Management.System.Repository.PatientRepository;
import com.HMSApp.Hospital.Management.System.Repository.PrescriptionRepository;

import jakarta.transaction.Transactional;

@Service
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepo;
    private final AppointmentRepository appointmentRepo; // assume exists
    private final DoctorRepository doctorRepo; // assume exists
    private final PatientRepository patientRepo; // assume exists

    public PrescriptionService(PrescriptionRepository prescriptionRepo,
                               AppointmentRepository appointmentRepo,
                               DoctorRepository doctorRepo,
                               PatientRepository patientRepo) {
        this.prescriptionRepo = prescriptionRepo;
        this.appointmentRepo = appointmentRepo;
        this.doctorRepo = doctorRepo;
        this.patientRepo = patientRepo;
    }

    @Transactional
    public Prescription createPrescription(Long appointmentId, Long doctorId, Long patientId, String notes, java.util.List<PrescriptionItem> items) {
        Appointment appt = appointmentRepo.findById(appointmentId).orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        Doctor doc = doctorRepo.findById(doctorId).orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        Patient pat = patientRepo.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        Prescription p = new Prescription();
        p.setAppointment(appt);
        p.setDoctor(doc);
        p.setPatient(pat);
        p.setIssuedDate(LocalDate.now());
        p.setNotes(notes);

        if (items != null) {
            for (PrescriptionItem it : items) p.addItem(it);
        }

        return prescriptionRepo.save(p);
    }
}
