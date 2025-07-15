package com.sprinbatchv1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprinbatchv1.service.StudentBatchService;

@RestController
public class StudentBatchController {
	
	@Autowired
	private StudentBatchService studentBatchService;
	
	@PostMapping("/importusers")
	public ResponseEntity<String> processRecords(){
		
		boolean jobLaunched = studentBatchService.launchTheJob();
		
		 if (jobLaunched) {
	            return ResponseEntity
	                    .status(HttpStatus.OK)
	                    .body("All Records Saved To DB. Please Check");
	        } else {
	            return ResponseEntity
	                    .status(HttpStatus.SERVICE_UNAVAILABLE)
	                    .body("Sorry, we couldn't start the import process right now. Please try again later.");
	        }
		
	}
}
