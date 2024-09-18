package com.demo.project.dao;

import java.util.List;

import com.demo.project.entity.Batch;

public interface BatchesDao {
	void save();

	void update();

	Batch findById();

	List<Batch> findAll();

}