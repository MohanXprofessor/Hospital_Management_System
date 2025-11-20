package com.HMSApp.Hospital.Management.System.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.HMSApp.Hospital.Management.System.Entity.Appointment;
import com.HMSApp.Hospital.Management.System.Entity.Doctor;
import com.HMSApp.Hospital.Management.System.Entity.Patient;
import com.HMSApp.Hospital.Management.System.Enum.AppointmentStatus;
import com.HMSApp.Hospital.Management.System.Repository.AppointmentRepository;
import com.HMSApp.Hospital.Management.System.Repository.DoctorRepository;
import com.HMSApp.Hospital.Management.System.Repository.PatientRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AppointmentServiceImp implements AppointmentService{
	
	
	private final AppointmentRepository appointmentRepository;
	private final DoctorRepository docRepo;
	private final PatientRepository patientRepo;


	public AppointmentServiceImp(AppointmentRepository appointmentRepository,DoctorRepository docRepo,PatientRepository patientRepo ) {
	this.appointmentRepository = appointmentRepository;
	this.docRepo=docRepo;
	this.patientRepo=patientRepo;
	}


	
    @Override
    @Transactional
    public Appointment create(Appointment request) {
        // Basic null checks for embedded doctor/patient references
        if (request == null) {
            throw new IllegalArgumentException("Appointment request cannot be null");
        }
        if (request.getDoctor() == null || request.getDoctor().getId() == null) {
            throw new IllegalArgumentException("doctorId is required on the appointment");
        }
        if (request.getPatient() == null || request.getPatient().getId() == null) {
            throw new IllegalArgumentException("patientId is required on the appointment");
        }

        // Load doctor and patient from DB (ensures they exist)
        Long doctorId = request.getDoctor().getId();
        Long patientId = request.getPatient().getId();

        Doctor doctor = docRepo.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + doctorId));
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + patientId));

        // Optional: validate appointment time within doctor's availability
        if (request.getTime() != null && doctor.getAvailableFrom() != null && doctor.getAvailableTo() != null) {
            LocalTime apptTime = request.getTime();
            if (apptTime.isBefore(doctor.getAvailableFrom()) || apptTime.isAfter(doctor.getAvailableTo())) {
                throw new IllegalArgumentException("Appointment time " + apptTime
                        + " is outside doctor's availability (" + doctor.getAvailableFrom() + " - " + doctor.getAvailableTo() + ")");
            }
        }

        // Ensure JPA will create a new row (not update)
        request.setId(null);

        // Attach managed doctor and patient entity instances to the appointment
        request.setDoctor(doctor);
        request.setPatient(patient);

        // Save appointment
        Appointment saved = appointmentRepository.save(request);

        // Keep both sides of relation in sync
        // (doctor.appointment is the list in your Doctor class; if you rename to appointments adjust accordingly)
        doctor.getAppointment().add(saved);      // add to doctor's list
        patient.getAppointments().add(saved);    // add to patient's list

        // Because we are transactional and doctor/patient are managed, no explicit save required for them
        return saved;
    }

	@Override
	public Appointment getById(Long id) {
		Optional<Appointment> opt = appointmentRepository.findById(id);
		return opt.orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
	}


	@Override
	public List<Appointment> getAll() {
		
		return appointmentRepository.findAll();
	}


	@Override
	public Appointment update(Long id, Appointment request) {
		Appointment existing = getById(id);
		// update allowed fields
		existing.setDate(request.getDate());
		existing.setTime(request.getTime());
		existing.setStatus(request.getStatus());
		// If you have doctor/patient relationships, update them here as needed
		return appointmentRepository.save(existing);
	}


	@Override
	public void delete(Long id) {
		Appointment existing = getById(id);
		appointmentRepository.delete(existing);
		
	}


	@Override
	public List<Appointment> findByDate(LocalDate date) {
		return appointmentRepository.findByDate(date);
	}


	@Override
	public List<Appointment> findByStatus(AppointmentStatus status) {
		return appointmentRepository.findByStatus(status);
	}


	@Override
	public Appointment changeStatus(Long id, AppointmentStatus status) {
		Appointment existing = getById(id);
		existing.setStatus(status);
		return appointmentRepository.save(existing);
	}
	


		

}
