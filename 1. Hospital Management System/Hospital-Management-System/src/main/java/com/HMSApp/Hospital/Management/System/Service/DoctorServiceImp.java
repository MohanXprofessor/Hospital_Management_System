package com.HMSApp.Hospital.Management.System.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.HMSApp.Hospital.Management.System.Entity.Doctor;
import com.HMSApp.Hospital.Management.System.Repository.DoctorRepository;

@Service
public class DoctorServiceImp implements DoctorService{

	// Constructor Injunction 
		 private final DoctorRepository doctorRepo;

		    public DoctorServiceImp(DoctorRepository doctorRepo) {
		        this.doctorRepo = doctorRepo;
		    }
		//Constructor Injunction
		    
		@Override
		public Doctor saveDoctor(Doctor doctor) {
			if (doctor.getEmail() != null && doctorRepo.existsByEmail(doctor.getEmail())) {
	            throw new IllegalArgumentException("Doctor with email " + doctor.getEmail() + " already exists");
	        }
	        validateAvailability(doctor);
	        return doctorRepo.save(doctor);
		} 

		@Override
		public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
			 // Find the existing doctor by ID
	        Doctor existingDoctor = doctorRepo.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + id));

	        // Update fields (you can customize which fields are updatable)
	        existingDoctor.setFullName(updatedDoctor.getFullName());
	        
	        
	        existingDoctor.setEmail(updatedDoctor.getEmail());
	        existingDoctor.setPhone(updatedDoctor.getPhone());
	        existingDoctor.setGender(updatedDoctor.getGender());
	        existingDoctor.setSpecialization(updatedDoctor.getSpecialization());
	        existingDoctor.setQualification(updatedDoctor.getQualification());
	        existingDoctor.setExperienceYears(updatedDoctor.getExperienceYears());
	        existingDoctor.setAvailableFrom(updatedDoctor.getAvailableFrom());
	        existingDoctor.setAvailableTo(updatedDoctor.getAvailableTo());
	        existingDoctor.setActive(updatedDoctor.isActive());

	        validateAvailability(existingDoctor);
	        

	        return doctorRepo.save(existingDoctor);
	    }

		@Override
		public List<Doctor> getAllDoctors() {
			// TODO Auto-generated method stub
			return doctorRepo.findAll();
		}

		@Override
		public Optional<Doctor> getDoctorById(Long id) {
			// TODO Auto-generated method stub
			return doctorRepo.findById(id);
		}

		@Override
		public void deleteDoctor(Long id) {
			
			if (!doctorRepo.existsById(id)) {
	            throw new IllegalArgumentException("Doctor not found with ID: " + id);
	        }
	        doctorRepo.deleteById(id);
			
		}

		@Override
		public List<Doctor> getActiveDoctors() {
			// TODO Auto-generated method stub
			return doctorRepo.findByActiveTrue();
		}

		@Override
		public Doctor changeDoctorStatus(Long id, boolean active) {
			Doctor doctor = doctorRepo.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + id));
	        doctor.setActive(active);
	        return doctorRepo.save(doctor);
		}
		
		private void validateAvailability(Doctor doctor) {
	        // Example: Validate doctor's availability time
	        if (doctor.getAvailableFrom() == null || doctor.getAvailableTo() == null) {
	            throw new IllegalArgumentException("Doctor availability time must be provided");
	        }
	        if (doctor.getAvailableFrom().isAfter(doctor.getAvailableTo())) {
	            throw new IllegalArgumentException("Available from time cannot be after available to time");
	        }
	    }

}
