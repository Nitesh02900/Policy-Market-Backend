package com.techlabs.insurance.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entity.Message;
import com.techlabs.insurance.exception.CustomerNotFoundException;
import com.techlabs.insurance.payload.MessageDto;
import com.techlabs.insurance.payload.UpdateCustomer;
import com.techlabs.insurance.service.MessageService;

@RestController
@RequestMapping("/insuranceapp")
public class MessageControlller {
	
	
	@Autowired
	private MessageService messageService;
	
	@PostMapping("/query")
	public  ResponseEntity<String> AddMessage(@RequestBody Message message, @RequestParam  String username)
	{
		String response =  messageService.AddQuery(message,username );
	return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/query")
	public ResponseEntity<Page<Message>> GetQueryByUsername(@RequestParam Map<String,String>params)
	{
		Page<Message> messagePage=null;
		
		int pagenumber =0;
		int pagesize = 30;
		String username = params.get("username");
	
		if(params.isEmpty())
		{
			messageService.findAllMessageUsername(username);
		}
		else
		{
			
			if(params.containsKey("pagenumber"))
			{
				pagenumber = Integer.parseInt(params.get("pagenumber"));
			}
			 if (params.containsKey("pagesize")) {
			       
				 pagesize = Integer.parseInt(params.get("pagesize"));
			 } 
		}
		messagePage = messageService.getAllMessagePageWise(pagenumber, pagesize, username);
			
		if(messagePage.getTotalElements()==0)
		{
			 throw new CustomerNotFoundException();
		}
		HttpHeaders header  = new HttpHeaders();
		header.set("X-Total-Count", String.valueOf(messagePage.getTotalElements()));
		return ResponseEntity.ok().headers(header).body(messagePage);
		
	}
	
	@GetMapping("/querys")
	public ResponseEntity<Page<MessageDto>> GetAllQuery(@RequestParam Map<String,String>params)
	{
		Page<MessageDto> messagePage=null;
		int pagenumber =0;
		int pagesize = 30;
	
		if(params.isEmpty())
		{
			messageService.findAllMessage();
		}
		else
		{
			if(params.containsKey("pagenumber"))
			{
				pagenumber = Integer.parseInt(params.get("pagenumber"));
			}
			if (params.containsKey("pagesize")) {
			     pagesize = Integer.parseInt(params.get("pagesize"));
			 } 
		}
		messagePage = messageService.findAllMessageDtoPageWise(pagenumber, pagesize);
			
		if(messagePage.getTotalElements()==0)
		{
			 throw new CustomerNotFoundException();
		}
		HttpHeaders header  = new HttpHeaders();
		header.set("X-Total-Count", String.valueOf(messagePage.getTotalElements()));
		return ResponseEntity.ok().headers(header).body(messagePage);
	}
	
	
	@PutMapping("/query")
	public  ResponseEntity<String> UpdateQuery( @RequestParam int id, @RequestBody String answer)
	{
		String response = messageService.SaveMessageById(id, answer);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED); 
	}
	

}
