package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.entity.EmployeeEntity;
@Transactional
public interface EmployeeDAO extends JpaRepository<EmployeeEntity, Long>{

}
