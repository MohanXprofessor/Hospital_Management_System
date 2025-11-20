package com.HMSApp.Hospital.Management.System.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HMSApp.Hospital.Management.System.Entity.Appointment;
import com.HMSApp.Hospital.Management.System.Enum.AppointmentStatus;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	List<Appointment> findByDate(LocalDate date);


	// Find by status
	List<Appointment> findByStatus(AppointmentStatus status);

}

