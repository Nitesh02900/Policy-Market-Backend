package com.techlabs.insurance.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.techlabs.insurance.entity.Message;
import com.techlabs.insurance.payload.MessageDto;

public interface MessageService {

	String AddQuery(Message query, String username );
	List<Message> findAllMessageUsername(String username);
	Page<Message> getAllMessagePageWise(int pageNumber, int pageSize, String username);

	List<MessageDto> findAllMessage();
	
	String SaveMessageById (int id , String answer);
	Page<MessageDto> findAllMessageDtoPageWise(int pageNumber, int pageSize);
	
}
