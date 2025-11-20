package com.HMSApp.Hospital.Management.System.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.HMSApp.Hospital.Management.System.Entity.Patient;
import com.HMSApp.Hospital.Management.System.Repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PatientServiceImpl implements PatientService{

	
    private final PatientRepository patientRepo;
    
    public PatientServiceImpl(PatientRepository patientRepo) {
        this.patientRepo = patientRepo;
    }

    @Override
    public Patient create(Patient request) {
        request.setId(null); // ensure new record
        return patientRepo.save(request);
    }

    @Override
    public Patient getById(Long id) {
        return patientRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + id));
    }

    @Override
    public List<Patient> getAll() {
        return patientRepo.findAll();
    }

    @Override
    public Patient update(Long id, Patient request) {
        Patient existing = getById(id);
        existing.setName(request.getName());
        existing.setEmail(request.getEmail());
        existing.setDateOfBirth(request.getDateOfBirth());
        existing.setGender(request.getGender());
        existing.setPhone(request.getPhone());
        existing.setAddress(request.getAddress());
        return patientRepo.save(existing);
    }

    @Override
    public void delete(Long id) {
        Patient existing = getById(id);
        patientRepo.delete(existing);
    }
}