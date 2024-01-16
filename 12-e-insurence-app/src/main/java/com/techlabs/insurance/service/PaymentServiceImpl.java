package com.techlabs.insurance.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entity.Payment;
import com.techlabs.insurance.entity.Policy;
import com.techlabs.insurance.payload.PaymentDto;
import com.techlabs.insurance.repository.PaymentRepository;
import com.techlabs.insurance.repository.PolicyRepository;

@Service
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	private PaymentRepository paymentRepo;
	@Autowired
	private PolicyRepository policyRepo;
	
	@Override
	public String addPayment(Payment payment, int policyNumber) {
	
		
		Date currentDate = new Date();
		Policy policy = policyRepo.findByPolicyNumberAndActive(policyNumber, true);
		if(policy==null)
		{
			System.out.println("policy notfound");
		}
		if(policy!=null)
		{
			payment.setPolicy(policy);
			payment.setDate(currentDate);
			payment.setStatus("paid");
			paymentRepo.save(payment);
			System.out.println("payment is saved");
			return "Payment is save";
		}
		
		
		return "Payment is not saved ";
	}

	@Override
	public List<PaymentDto> getAllPayment() {
		 List<Payment> payments = paymentRepo.findAll();
	        return payments.stream()
	                .map(this::convertToDto)
	                .collect(Collectors.toList());
	}
	private PaymentDto convertToDto(Payment payment) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPaymentId(payment.getPaymentId());
        paymentDto.setInstallmentNo(payment.getInstallmentNo());
        paymentDto.setCardNumber(payment.getCardNumber());
        paymentDto.setAmount(payment.getAmount());
        paymentDto.setNameOnCard(payment.getNameOnCard());
        paymentDto.setDate(payment.getDate());
        paymentDto.setStatus(payment.getStatus());
        // Assuming Policy has a method to get the PolicyNo, replace it with the actual method.
        paymentDto.setPolicyNo(payment.getPolicy().getPolicyNumber());
        return paymentDto;
    }
	

}
