package com.demo.project.operations;

import java.util.Scanner;
import com.demo.project.colour.*;
import com.demo.project.dao.MatchSessionDao;
import com.demo.project.dao.impl.MatchSessionDaoImpl;

public class MatchSessionOperations {

	public void matchSessionOperations() {
		Colour col = new Colour();
		MatchSessionDao matchSessionDao = new MatchSessionDaoImpl();
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println(col.BG_RED + col.BOLD_WHTE + "Match Session Operations:");
			System.out.println(col.YELLOW + "\t\t1. Register Match Session");
			System.out.println(col.YELLOW + "\t\t2. View Match Session by ID");
			System.out.println(col.YELLOW + "\t\t3. Update Match Session");
			System.out.println(col.YELLOW + "\t\t4. List Match Sessions");
			System.out.println(col.YELLOW + "\t\t0. Back");
			int choice = sc.nextInt();
			sc.nextLine(); // consume newline
			switch (choice) {
			case 1:
				matchSessionDao.save();
				break;
			case 2:
				matchSessionDao.findById();
				break;
			case 3:
				matchSessionDao.update();
				break;
			case 4:
				matchSessionDao.findAll();
				break;
			case 0:
				return;
			default:
				System.out.println("Invalid option, please try again.");
			}
		}
	}

}
