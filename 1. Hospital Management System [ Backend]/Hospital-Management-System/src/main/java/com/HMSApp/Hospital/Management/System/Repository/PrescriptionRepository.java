package com.HMSApp.Hospital.Management.System.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HMSApp.Hospital.Management.System.Entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

	
	 List<Prescription> findByPatient_Id(Long patientId);
	    List<Prescription> findByDoctor_Id(Long doctorId);
	    List<Prescription> findByAppointment_Id(Long appointmentId);
}
