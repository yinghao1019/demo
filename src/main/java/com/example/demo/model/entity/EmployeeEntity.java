package com.example.demo.model.entity;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.Data;

@Entity(name = "employee")
@Data
public class EmployeeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "oid")
	private Long oid;

	@Column(name = "firstname")
	private String firstName;

	@Column(name = "lastname")
	private String lastName;
	
	@Column(name = "age")
	private Integer age;
	
	@Column
	private OffsetDateTime createdTimestamp; 
	
	@PrePersist
	public void prePersist() {
		createdTimestamp=OffsetDateTime.now();
	}
}
