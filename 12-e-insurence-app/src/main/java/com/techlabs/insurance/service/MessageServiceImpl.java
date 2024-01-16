package com.techlabs.insurance.service;

import com.techlabs.insurance.entity.Customer;
import com.techlabs.insurance.entity.Message;
import com.techlabs.insurance.entity.UserDetails;
import com.techlabs.insurance.payload.MessageDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.repository.CustomerRepository;
import com.techlabs.insurance.repository.MessageRepository;
import com.techlabs.insurance.repository.UserDetailsRepository;

@Service
public class MessageServiceImpl implements MessageService{

	
	@Autowired
	private MessageRepository messageRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	 private UserDetailsRepository userDetailsRepo;
	 
	
	@Override
	public String AddQuery(Message query, String username) {
		
		query.setStatus("NotAnswered");
		UserDetails userDetails = userDetailsRepo.findByUsername(username);
		List<Customer> customers = customerRepo.findAll();
		for (Customer customer : customers) {
            if (customer.getUserdetails().getUserDetailId()== userDetails.getUserDetailId()) 
            {
              query.setCustomer(customer);
              messageRepo.save(query);
              return "query is added";
            }
        }
		
		
		
		return "Query is not added";
	}


	
	@Override
	public List<Message> findAllMessageUsername(String username) {
		UserDetails userDetails = userDetailsRepo.findByUsername(username);
		List<Customer> customers = customerRepo.findAll();
		for (Customer customer : customers) {
            if (customer.getUserdetails().getUserDetailId()== userDetails.getUserDetailId()) 
            {
              return customer.getMessage();
            }
        }
		return null;
	}


	@Override
	public Page<Message> getAllMessagePageWise(int pageNumber, int pageSize, String username) {
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		UserDetails userDetails = userDetailsRepo.findByUsername(username);
		List<Customer> customers = customerRepo.findAll();
		List<Message> messages = new ArrayList<Message>();
		for (Customer customer : customers) {
            if (customer.getUserdetails().getUserDetailId()== userDetails.getUserDetailId()) 
            {
              messages=customer.getMessage();
            }
        }
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), messages.size());
		return new PageImpl<>(messages.subList(start, end), pageable, messages.size());

	}
	
//	@Override
//	public Page<Message> findAllMessagePageWise(int pageNumber , int pageSize){
//		
//		Pageable pageable = PageRequest.of(pageNumber, pageSize);
//		Page<Message> messagePage= messageRepo.findAll(pageable);
//		return messagePage;
//
//	}

	@Override
	public Page<MessageDto> findAllMessageDtoPageWise(int pageNumber, int pageSize) {
	    Pageable pageable = PageRequest.of(pageNumber, pageSize);
	    Page<Message> messagePage = messageRepo.findAll(pageable);

	    return messagePage.map(message -> {
	        String firstName = message.getCustomer().getUserdetails().getFirstname();
	        String lastName = message.getCustomer().getUserdetails().getLastname();

	        MessageDto messageDto = new MessageDto(
	            message.getId(),
	            message.getQuestion(),
	            message.getAnswer(),
	            message.getStatus(),
	            firstName,
	            lastName
	        );

	        return messageDto;
	    });
	}



	@Override
	public List<MessageDto> findAllMessage() {
	    List<Message> messages = messageRepo.findAll();
	    
	    List<MessageDto> messageDtos = new ArrayList<>();
	    
	    for (Message message : messages) {
	        String firstName = message.getCustomer().getUserdetails().getFirstname();
	        String lastName = message.getCustomer().getUserdetails().getLastname();

	        MessageDto messageDto = new MessageDto(
	            message.getId(),
	            message.getQuestion(),
	            message.getAnswer(),
	            message.getStatus(),
	            firstName,
	            lastName
	        );

	        messageDtos.add(messageDto);
	    }

	    return messageDtos;
	}




	@Override
	public String SaveMessageById(int id, String answer) {
	    Optional<Message> optionalMessage = messageRepo.findById(id);

	    if (optionalMessage.isPresent()) {
	        Message message = optionalMessage.get();
	        message.setAnswer(answer);
	        message.setStatus("Answered"); 

	        messageRepo.save(message);
	        return "Saved Answered";
	    } else {
	        return "No such Message";
	    }
	}

	
	



}
