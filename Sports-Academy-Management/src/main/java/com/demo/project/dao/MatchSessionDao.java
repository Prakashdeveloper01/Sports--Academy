package com.demo.project.dao;

import java.util.List;

import com.demo.project.entity.MatchSession;

public interface MatchSessionDao {
	void save();

	void update();

	MatchSession findById();

	List<MatchSession> findAll();

}
