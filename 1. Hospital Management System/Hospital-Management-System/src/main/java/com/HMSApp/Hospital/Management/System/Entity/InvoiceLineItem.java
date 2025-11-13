package com.HMSApp.Hospital.Management.System.Entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "invoice_line_items")
@Data
public class InvoiceLineItem {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne 
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    private String description;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal taxPercent; // e.g. 18
    private BigDecimal lineTotal; // calculated (qty*unitPrice + tax)
}
