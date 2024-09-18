package com.demo.project.operations;

import java.util.Scanner;
import com.demo.project.colour.*;
import com.demo.project.dao.CoachDao;
import com.demo.project.dao.impl.CoachDaoImpl;

public class CoachOperations {
	Colour col = new Colour();

	public void coachOperations() {
		Scanner sc = new Scanner(System.in);
		CoachDao coachDao = new CoachDaoImpl();
		while (true) {
			System.out.println(col.BG_RED + col.BOLD_WHTE + "Coach Operations:");
			System.out.println(col.GREEN + "\t\t1. Register Coach");
			System.out.println(col.GREEN + "\t\t2. View Coach by ID");
			System.out.println(col.GREEN + "\t\t3. Update Coach");
			System.out.println(col.GREEN + "\t\t4. List Coaches");
			System.out.println(col.GREEN + "\t\t0. Back");
			int choice = sc.nextInt();
			sc.nextLine(); // consume newline
			switch (choice) {
			case 1:
				coachDao.save();
				break;
			case 2:
				coachDao.findById();
				break;
			case 3:
				coachDao.update();
				break;
			case 4:
				coachDao.findAll();
				break;
			case 0:
				return;
			default:
				System.out.println("Invalid option, please try again.");
			}
		}
	}

}
