package com.techlabs.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entity.FileDB;


public interface FileDBRepository extends JpaRepository<FileDB, String>{

}
