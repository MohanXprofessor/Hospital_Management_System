package com.HMSApp.Hospital.Management.System.Entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "prescription_items")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data

public class PrescriptionItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore 
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    private String medicationName;
    private String dosage;      // e.g. "1 tab TID"
    private Integer quantity;
    private BigDecimal unitPrice; // optional, used if you want price here
    private String instructions;
}

