package com.demo.project.dao;

import java.util.List;

import com.demo.project.entity.Sport;

public interface SportDao {
	void save();

	void update();

	Sport findById();

	List<Sport> findAll();

}