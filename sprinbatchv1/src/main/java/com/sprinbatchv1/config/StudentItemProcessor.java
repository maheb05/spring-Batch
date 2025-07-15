package com.sprinbatchv1.config;

import org.springframework.batch.item.ItemProcessor;

import com.sprinbatchv1.entity.Student;

public class StudentItemProcessor implements ItemProcessor<Student, Student> {

	@Override
	public Student process(Student student) throws Exception {
		return student;
	}

}
