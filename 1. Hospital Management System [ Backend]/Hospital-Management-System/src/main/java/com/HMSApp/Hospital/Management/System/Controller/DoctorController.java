package com.HMSApp.Hospital.Management.System.Controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.HMSApp.Hospital.Management.System.Entity.Doctor;
import com.HMSApp.Hospital.Management.System.Service.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
	
	private final DoctorService docService;

    public DoctorController(DoctorService docService) {
        this.docService = docService;
    }
	
    // Create
    @PostMapping
    public ResponseEntity<Doctor> create(@Valid @RequestBody Doctor doctor) {
        Doctor saved = docService.saveDoctor(doctor);
        // Build Location: /api/doctors/{id}
        return ResponseEntity
                .created(URI.create("/api/doctors/" + saved.getId()))
                .body(saved);
    }
	
    // Read all
    @GetMapping
    public ResponseEntity<List<Doctor>> findAll() {
        return ResponseEntity.ok(docService.getAllDoctors());
    }

    // Read active only
    @GetMapping("/active")
    public ResponseEntity<List<Doctor>> findActive() {
        return ResponseEntity.ok(docService.getActiveDoctors());
    }
    
 // Read by id
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> findById(@PathVariable Long id) {
        Optional<Doctor> doctor = docService.getDoctorById(id);
        return doctor.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update (full replace of editable fields)
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> update(@PathVariable Long id, @Valid @RequestBody Doctor updated) {
        Doctor saved = docService.updateDoctor(id, updated);
        return ResponseEntity.ok(saved);
    }
    
    // Change status (activate/deactivate)
    @PatchMapping("/{id}/status")
    public ResponseEntity<Doctor> changeStatus(@PathVariable Long id,
                                               @RequestParam boolean active) {
        Doctor saved = docService.changeDoctorStatus(id, active);
        return ResponseEntity.ok(saved);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        docService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
    

}
