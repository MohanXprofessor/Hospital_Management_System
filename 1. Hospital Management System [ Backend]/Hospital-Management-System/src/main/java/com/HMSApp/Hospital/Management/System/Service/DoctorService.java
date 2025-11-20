package com.HMSApp.Hospital.Management.System.Service;

import java.util.List;
import java.util.Optional;

import com.HMSApp.Hospital.Management.System.Entity.Doctor;

public interface DoctorService {
	Doctor saveDoctor(Doctor doctor);

    Doctor updateDoctor(Long id, Doctor doctor);

    List<Doctor> getAllDoctors();

    Optional<Doctor> getDoctorById(Long id);

    void deleteDoctor(Long id);

    List<Doctor> getActiveDoctors();

    Doctor changeDoctorStatus(Long id, boolean active);
}
