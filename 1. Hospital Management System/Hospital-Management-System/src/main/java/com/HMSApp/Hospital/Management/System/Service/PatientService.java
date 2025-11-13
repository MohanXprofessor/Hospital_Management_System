package com.HMSApp.Hospital.Management.System.Service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HMSApp.Hospital.Management.System.Entity.Patient;

public interface PatientService  {
	
	 	Patient create(Patient request);
	 	
	    Patient getById(Long id);
	    
	    List<Patient> getAll();
	    
	    Patient update(Long id, Patient request);
	    
	    void delete(Long id);

}
