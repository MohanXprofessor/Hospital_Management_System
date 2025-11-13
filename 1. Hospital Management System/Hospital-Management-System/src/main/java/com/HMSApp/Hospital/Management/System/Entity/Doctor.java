package com.HMSApp.Hospital.Management.System.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.HMSApp.Hospital.Management.System.Enum.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="Doctors")
public class Doctor {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    // Doctor name
	    @NotBlank(message = "Doctor name is required")
	    @Column(name = "full_name", nullable = false, length = 150)
	    private String fullName;

	    // Email
	    @Email(message = "Invalid email format")
	    @Column(name = "email", length = 120, unique = true)
	    private String email;

	    // Phone (10–15 digits, optional + at start)
	    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone must be 10–15 digits, optional +")
	    @Column(name = "phone", length = 16)
	    private String phone;

	    // Date of birth
	    @NotNull(message = "Date of birth is required")
	    @Past(message = "Date of birth must be in the past")
	    @Column(name = "date_of_birth", nullable = false)
	    private LocalDate dateOfBirth;

	    // Gender
	    @Enumerated(EnumType.STRING)
	    @Column(name = "gender", length = 10)
	    private Gender gender;
	   

	    // Specialization (e.g., Cardiology)
	    @NotBlank(message = "Specialization is required")
	    @Column(name = "specialization", nullable = false, length = 100)
	    private String specialization;

	    // Qualification (e.g., MBBS, MD)
	    @NotBlank(message = "Qualification is required")
	    @Column(name = "qualification", nullable = false, length = 100)
	    private String qualification;

	    // Experience in years
	    @NotNull(message = "Experience is required")
	    @Min(value = 0, message = "Experience must be 0 or greater")
	    @Column(name = "experience_years", nullable = false)
	    private Integer experienceYears;

	    // Availability window (daily hours)
	    @Column(name = "available_from")
	    private LocalTime availableFrom;   // e.g., 09:00

	    @Column(name = "available_to")
	    private LocalTime availableTo;     // e.g., 17:30

	    // Automatically set when the doctor is created
	    @CreationTimestamp
	    @Column(name = "created_at", nullable = false, updatable = false)
	    private LocalDateTime createdAt;

	    // Active / Inactive flag
	    @Builder.Default
	    @Column(name = "active", nullable = false)
	    private boolean active = true;

		

	// One Doctor can have Many Appointment
	    @OneToMany(mappedBy="doctor", cascade=CascadeType.ALL,orphanRemoval=true)
	    @ToString.Exclude
	    @Builder.Default
	    @JsonIgnore
	    private List<Appointment> appointment=new ArrayList<>();

	    
	}

	


