package com.HMSApp.Hospital.Management.System.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HMSApp.Hospital.Management.System.Entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

	
		List<Prescription> findByPatientId(Long patientId);
		
	    List<Prescription> findByAppointmentId(Long appointmentId);
}
