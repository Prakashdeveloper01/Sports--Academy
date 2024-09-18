package com.demo.project.operations;

import java.util.Scanner;
import com.demo.project.colour.*;
import com.demo.project.dao.SportDao;
import com.demo.project.dao.impl.SportDaoImpl;

public class SportOperations {

	public void sportOperations() {
		Colour col = new Colour();
		Scanner sc = new Scanner(System.in);
		SportDao sportDao = new SportDaoImpl();
		while (true) {
			System.out.println(col.BG_RED + col.BOLD_WHTE + "Sport Operations:");
			System.out.println(col.CYAN+"\t\t1. Register Sport");
			System.out.println(col.CYAN+"\t\t2. View Sport by ID");
			System.out.println(col.CYAN+"\t\t3. Update Sport");
			System.out.println(col.CYAN+"\t\t4. List Sports");
			System.out.println(col.CYAN+"\t\t0. Back");
			int choice = sc.nextInt();
			sc.nextLine(); // consume newline
			switch (choice) {
			case 1:
				sportDao.save();
				break;
			case 2:
				sportDao.findById();
				break;
			case 3:
				sportDao.update();
				break;
			case 4:
				sportDao.findAll();
				break;
			case 0:
				return;
			default:
				System.out.println("Invalid option, please try again.");
			}
		}
	}

}
