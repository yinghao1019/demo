package com.example.demo.model.entity;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Table(name = "employee")
@Entity
@Data
public class EmployeeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "oid")
	private Long oid;

	@Column(name = "firstname")
	@NotNull
	private String firstName;

	@Column(name = "lastname")
	@NotNull
	private String lastName;
	
	@Column(name = "age")
	@NotNull
	private Integer age;
	
	@Column
	@NotNull
	private OffsetDateTime createdTimestamp; 
	
	@PrePersist
	public void prePersist() {
		createdTimestamp=OffsetDateTime.now();
	}
}
