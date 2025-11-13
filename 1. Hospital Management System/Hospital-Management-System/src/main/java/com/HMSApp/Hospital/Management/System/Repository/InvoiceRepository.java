package com.HMSApp.Hospital.Management.System.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HMSApp.Hospital.Management.System.Entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
	
	Optional<Invoice> findByInvoiceNo(String invoiceNo);

}
