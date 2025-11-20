package com.HMSApp.Hospital.Management.System.Service;

import java.time.LocalDate;
import java.util.List;

import com.HMSApp.Hospital.Management.System.Entity.Appointment;
import com.HMSApp.Hospital.Management.System.Enum.AppointmentStatus;

public interface AppointmentService {
	
	Appointment create(Appointment request);
	Appointment getById(Long id);
	List<Appointment> getAll();
	Appointment update(Long id, Appointment request);
	void delete(Long id);


	// Queries
	List<Appointment> findByDate(LocalDate date);
	List<Appointment> findByStatus(AppointmentStatus status);


	// Status change
	Appointment changeStatus(Long id, AppointmentStatus status);
}
