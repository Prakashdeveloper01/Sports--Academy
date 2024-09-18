package com.demo.project.operations;

import java.util.Scanner;

import com.demo.project.dao.StudentDao;
import com.demo.project.dao.impl.StudentDaoImpl;

public class StudentOperation {
	
		 public void studentOperations() {
			 Scanner sc = new Scanner(System.in);
			 StudentDao sdo = new StudentDaoImpl();
				while (true) {
					System.out.println("Student Operations:");
					System.out.println("1. Register Student");
					System.out.println("2. View Student by ID");
					System.out.println("3. Update Student");
					System.out.println("4. List Students");
					System.out.println("0. Back");
					int choice = sc.nextInt();
					sc.nextLine(); // consume newline
					switch (choice) {
					case 1:
						sdo.saveStudent();
						break;
					case 2:
						sdo.findById();
						break;
					case 3:

						sdo.update();
						break;
					case 4:
						sdo.findAll();
						break;
					case 0:
						return;
					default:
						System.out.println("Invalid option, please try again.");
					}
				}
			} 
}
