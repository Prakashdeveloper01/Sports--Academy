package com.demo.project.operations;

import java.util.Scanner;
import com.demo.project.colour.*;
import com.demo.project.dao.PaymentDao;
import com.demo.project.dao.impl.PaymentDaoImpl;

public class PaymentOperations {

	public void paymentOperations() {
		Colour col = new Colour();
		PaymentDao paymentDao = new PaymentDaoImpl();
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println(col.BG_RED + col.BOLD_WHTE + "Payment Operations:");
			System.out.println(col.PURPLE + "\t\t1. Register Payment");
			System.out.println(col.PURPLE + "\t\t2. View Payment by ID");
			System.out.println(col.PURPLE + "\t\t3. Update Payment");
			System.out.println(col.PURPLE + "\t\t4. List Payments");
			System.out.println(col.PURPLE + "\t\t0. Back");
			int choice = sc.nextInt();
			sc.nextLine(); // consume newline
			switch (choice) {
			case 1:
				paymentDao.save();
				break;
			case 2:
				paymentDao.findById();
				break;
			case 3:
				paymentDao.update();
				break;
			case 4:
				paymentDao.findAll();
				break;
			case 0:
				return;
			default:
				System.out.println("Invalid option, please try again.");
			}
		}
	}

}
