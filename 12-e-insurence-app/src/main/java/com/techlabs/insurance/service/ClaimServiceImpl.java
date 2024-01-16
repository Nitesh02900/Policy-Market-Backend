package com.techlabs.insurance.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entity.Claim;
import com.techlabs.insurance.entity.Policy;
import com.techlabs.insurance.payload.ClaimDto;
import com.techlabs.insurance.repository.ClaimRepository;
import com.techlabs.insurance.repository.PolicyRepository;

@Service
public class ClaimServiceImpl implements ClaimService{

	@Autowired
	private ClaimRepository claimRepo;
	@Autowired
	private PolicyRepository policyRepo;
	
	@Override
	public String addClaim(Claim claim, int policyNumber) {
		Date currentDate = new Date();
		Policy policy = policyRepo.findByPolicyNumberAndActive(policyNumber, true);
		if(policy!=null)
		{
			claim.setPolicy(policy);
			claim.setDate(currentDate);
			claim.setStatus("Claimed");
			claimRepo.save(claim);
			return "Claim is save";
			
		}
		return "Claim is not saved";
	}
	
	@Override
	public List<ClaimDto> getAllClaim() {
		 List<Claim> claims = claimRepo.findAll();
	        return claims.stream()
	                .map(this::convertToDto)
	                .collect(Collectors.toList());
	}
	private ClaimDto convertToDto(Claim claim) {
        ClaimDto claimDto = new ClaimDto();
        claimDto.setClaimId(claim.getClaimId());
        claimDto.setInstallmentNo(claim.getInstallmentNo());
        claimDto.setCardNumber(claim.getCardNumber());
        claimDto.setAmount(claim.getAmount());
        claimDto.setNameOnCard(claim.getNameOnCard());
        claimDto.setDate(claim.getDate());
        claimDto.setStatus(claim.getStatus());
        // Assuming Policy has a method to get the PolicyNo, replace it with the actual method.
        claimDto.setPolicyNo(claim.getPolicy().getPolicyNumber());
        return claimDto;
    }

	
}
