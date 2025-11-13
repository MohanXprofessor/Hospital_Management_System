package com.HMSApp.Hospital.Management.System.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "prescriptions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Prescription {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) 
    @JoinColumn(name = "appointment_id")
    @JsonIgnore 
    private Appointment appointment; // assume exists


    private LocalDate issuedDate;

    private String notes;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore 
    private List<PrescriptionItem> items = new ArrayList<>();

    // helper to add item
    public void addItem(PrescriptionItem item){
        item.setPrescription(this);
        this.items.add(item);
    }
    
    @ManyToOne(optional = false) 
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private Doctor doctor;

    @ManyToOne(optional = false) 
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;

    // Expose IDs instead of serializing whole entities
    public Long getDoctorId() {
        return doctor == null ? null : doctor.getId();
    }

    public Long getPatientId() {
        return patient == null ? null : patient.getId();
    }

}