package com.demo.project.operations;

import java.util.Scanner;

import com.demo.project.dao.TrainingSessionDao;
import com.demo.project.dao.impl.TrainingSessionDaoImpl;

public class TrainingSessionOperations {
	
	public void trainingSessionOperations() {
		TrainingSessionDao trainingSessionDao = new TrainingSessionDaoImpl();
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Training Session Operations:");
			System.out.println("1. Register Training Session");
			System.out.println("2. View Training Session by ID");
			System.out.println("3. Update Training Session");
			System.out.println("4. List Training Sessions");
			System.out.println("0. Back");
			int choice = sc.nextInt();
			sc.nextLine(); // consume newline
			switch (choice) {
			case 1:
				trainingSessionDao.save();
				break;
			case 2:
				trainingSessionDao.findById();
				break;
			case 3:
				trainingSessionDao.update();
				break;
			case 4:
				trainingSessionDao.findAll();
				break;
			case 0:
				return;
			default:
				System.out.println("Invalid option, please try again.");
			}
		}
	}

}
