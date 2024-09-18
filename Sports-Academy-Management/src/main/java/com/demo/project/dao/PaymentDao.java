package com.demo.project.dao;

import java.util.List;

import com.demo.project.entity.Payment;

public interface PaymentDao {
	void save();

	void update();

	

	Payment findById();

	List<Payment> findAll();

}
