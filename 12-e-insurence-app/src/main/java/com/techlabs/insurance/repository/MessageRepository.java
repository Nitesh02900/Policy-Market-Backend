package com.techlabs.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entity.Message;

public interface MessageRepository  extends JpaRepository<Message,Integer>{

}
