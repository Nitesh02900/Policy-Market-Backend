package com.techlabs.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entity.Payment;

public interface PaymentRepository  extends JpaRepository<Payment, Integer>{

}
