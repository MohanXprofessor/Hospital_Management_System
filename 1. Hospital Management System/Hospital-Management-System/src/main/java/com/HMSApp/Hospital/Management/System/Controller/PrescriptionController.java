package com.HMSApp.Hospital.Management.System.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HMSApp.Hospital.Management.System.Entity.Prescription;
import com.HMSApp.Hospital.Management.System.Entity.PrescriptionItem;
import com.HMSApp.Hospital.Management.System.Service.PrescriptionService;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {
    private final PrescriptionService service;
    
    public PrescriptionController(PrescriptionService service)
    { 
    	this.service = service;
    	
    }

    // Create prescription (simple body)
    @PostMapping
    public Prescription create(@RequestBody CreatePrescriptionRequest req) {
        return service.createPrescription(req.appointmentId, req.doctorId, req.patientId, req.notes, req.items);
    }

    // DTO class inside controller for simplicity
    public static class CreatePrescriptionRequest {
        public Long appointmentId;
        public Long doctorId;
        public Long patientId;
        public String notes;
        
        public List<PrescriptionItem> items;
    }
}

