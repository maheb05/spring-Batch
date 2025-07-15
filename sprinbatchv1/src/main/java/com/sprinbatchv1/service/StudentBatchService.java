package com.sprinbatchv1.service;

import java.util.Date;

import org.apache.juli.logging.Log;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentBatchService {
	
	private final JobLauncher jobLauncher;
	
	private final Job job;
	
	public boolean launchTheJob() {
		
		try {
			JobParameters parameters = new JobParametersBuilder()
										.addLong("started at", new Date().getTime())
										.toJobParameters();
			jobLauncher.run(job, parameters);
			return true;
		} catch (Exception e) {
			log.error("Exception Occured in service layer"+ e.getMessage());
			return false;
		}
	}
	
}
