package com.demo.project.dao;

import java.util.List;

import com.demo.project.entity.TrainingSession;

import java.util.Date;

public interface TrainingSessionDao {
	void save();

	void update();

	TrainingSession findById();

	List<TrainingSession> findAll();

}