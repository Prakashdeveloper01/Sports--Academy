package com.demo.project.dao;

import java.util.List;

import com.demo.project.entity.Student;

public interface StudentDao {
	
	public void saveStudent();

	void update();

	Student findById();

	List<Student> findAll();

	
}