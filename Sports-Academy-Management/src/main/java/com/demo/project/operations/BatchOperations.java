package com.demo.project.operations;

import java.util.Scanner;
import com.demo.project.colour.*;
import com.demo.project.dao.BatchesDao;
import com.demo.project.dao.impl.BatchDaoImpl;

public class BatchOperations {
	Colour col = new Colour();

	public void batchOperations() {
		while (true) {
			BatchesDao batchDao = new BatchDaoImpl();
			Scanner sc = new Scanner(System.in);
			System.out.println(col.BG_RED + col.BOLD_WHTE+ "Batch Operations:" + col.RESET);
			System.out.println(col.BLUE+"\t\t1. Register Batch");
			System.out.println(col.BLUE+"\t\t2. View Batch by ID");
			System.out.println(col.BLUE+"\t\t3. Update Batch");
			System.out.println(col.BLUE+"\t\t4. List Batches");
			System.out.println(col.BLUE+"\t\t0. Back");
			int choice = sc.nextInt();
			sc.nextLine(); // consume newline
			switch (choice) {
			case 1:
				batchDao.save();
				break;
			case 2:
				batchDao.findById();
				break;
			case 3:
				batchDao.update();
				break;
			case 4:
				batchDao.findAll();
				break;
			case 0:
				return;
			default:
				System.out.println("Invalid option, please try again.");
			}
		}
	}

}
