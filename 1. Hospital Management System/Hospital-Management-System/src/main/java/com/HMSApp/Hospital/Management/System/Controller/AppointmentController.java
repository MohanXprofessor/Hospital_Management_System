package com.HMSApp.Hospital.Management.System.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

import com.HMSApp.Hospital.Management.System.Entity.Appointment;
import com.HMSApp.Hospital.Management.System.Entity.Doctor;
import com.HMSApp.Hospital.Management.System.Enum.AppointmentStatus;
import com.HMSApp.Hospital.Management.System.Service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
	
	private final AppointmentService appointmentService;


	public AppointmentController(AppointmentService appointmentService) {
	this.appointmentService = appointmentService;
	}


	 @PostMapping
	    public ResponseEntity<Appointment> create(@RequestBody Appointment request) {
	        Appointment created = appointmentService.create(request);
	        return ResponseEntity.status(201).body(created);
	    }


	@GetMapping
	public ResponseEntity<List<Appointment>> getAll() {
	return ResponseEntity.ok(appointmentService.getAll());
	}


	@GetMapping("/{id}")
	public ResponseEntity<Appointment> getById(@PathVariable Long id) {
	return ResponseEntity.ok(appointmentService.getById(id));
	}


	@PutMapping("/{id}")
	public ResponseEntity<Appointment> update(@PathVariable Long id, @RequestBody Appointment request) {
	return ResponseEntity.ok(appointmentService.update(id, request));
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
	appointmentService.delete(id);
	return ResponseEntity.noContent().build();
	}


	@GetMapping("/date/{date}")
	public ResponseEntity<List<Appointment>> getByDate(
	@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
	return ResponseEntity.ok(appointmentService.findByDate(date));
	}


	@PatchMapping("/{id}/status")
	public ResponseEntity<Appointment> changeStatus(@PathVariable Long id, @RequestParam AppointmentStatus status) {
	return ResponseEntity.ok(appointmentService.changeStatus(id, status));
	}

}
