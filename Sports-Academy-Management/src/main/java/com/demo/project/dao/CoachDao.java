package com.demo.project.dao;

import com.demo.project.entity.Coach;

import java.util.List;

public interface CoachDao {
	void save();

	void update();

	Coach findById();

	List<Coach> findAll();

}
