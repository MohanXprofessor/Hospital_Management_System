package com.HMSApp.Hospital.Management.System.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HMSApp.Hospital.Management.System.Entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	
	boolean existsByEmail(String email);
	
	List<Doctor> findByActiveTrue();
}
