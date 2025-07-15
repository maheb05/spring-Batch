package com.sprinbatchv1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprinbatchv1.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
