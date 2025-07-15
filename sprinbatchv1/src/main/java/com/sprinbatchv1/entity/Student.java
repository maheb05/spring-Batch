package com.sprinbatchv1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String firstName;
	
	private String lastName;
	
	private String fatherName;
	
	private String motherName;
	
	private String university;
	
	private String specialization;
	
	private String email;
	
	private String mobileNumber;
	
	private String city;
	
	private String country;
}
