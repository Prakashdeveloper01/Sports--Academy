package com.demo.project.Sports_Academy_Management;

import com.demo.project.operations.*;
import com.demo.project.dao.impl.*;
import java.util.Scanner;
import com.demo.project.colour.*;

public class App {

	public static void main(String[] args) {

		Colour col = new Colour();
		Scanner sc = new Scanner(System.in);
		AdminDaoimpl adminDao = new AdminDaoimpl();
		StudentOperation studentOperation = new StudentOperation();
		CoachOperations coachOperations = new CoachOperations();
		BatchOperations batchOperations = new BatchOperations();
		MatchSessionOperations matchSessionOperations = new MatchSessionOperations();
		PaymentOperations paymentOperations = new PaymentOperations();
		TrainingSessionOperations trainingSessionOperations = new TrainingSessionOperations();
		SportOperations sportOperations = new SportOperations();
		boolean loggedIn = false;

		while (!loggedIn) {
			System.out.print(col.BOLD_PURPLE + "Enter Admin Username: " + col.RESET);
			String username = sc.next();

			System.out.print(col.BOLD_PURPLE + "Enter Admin Password: " + col.RESET);
			String password = sc.next();

			loggedIn = adminDao.login(username, password);

			if (!loggedIn) {
				System.out.println("Invalid username or password. Please try again.");
			}
		}
		System.out.println(col.BOLD_PURPLE + "Login successful" + col.BOLD_RED + " Welcome!" + col.RESET);
		System.out.println(col.BOLD_YELLOW
				+ "\t\t\t\t\t\t\t\t\t-------------------------------------------------------------------------------------"
				+ col.RESET);

		System.out.println(col.BOLD_GREEN + col.Blnk
				+ "\t\t\t\t\t\t\t\t\t    SPORTS ACADEMY --TRY YOUR BEST UNTIL YOU ARE IN REST  " + "" + col.RESET);

		System.out.println(col.BOLD_YELLOW
				+ "\t\t\t\t\t\t\t\t\t-------------------------------------------------------------------------------------"
				+ col.RESET);
		while (true) {

			System.out.println(col.BG_BLUE + col.BOLD_WHTE + "Modules : " + col.RESET);
			System.out.println(col.PURPLE + "\t\t1. Student" + col.RESET);
			System.out.println(col.PURPLE + "\t\t2. Coach" + col.RESET);
			System.out.println(col.PURPLE + "\t\t3. Batch" + col.RESET);
			System.out.println(col.PURPLE + "\t\t4. Match Session" + col.RESET);
			System.out.println(col.PURPLE + "\t\t5. Payment" + col.RESET);
			System.out.println(col.PURPLE + "\t\t6. Training Session" + col.RESET);
			System.out.println(col.PURPLE + "\t\t7. Sport" + col.RESET);
			System.out.println(col.PURPLE + "\t\tExit" + col.RESET);
			System.out.println(col.BOLD_YELLOW + "Select the module:" + col.RESET);
			int entityChoice = sc.nextInt();
			switch (entityChoice) {
			case 1:
				studentOperation.studentOperations();
				break;
			case 2:
				coachOperations.coachOperations();
				break;
			case 3:
				batchOperations.batchOperations();
				break;
			case 4:
				matchSessionOperations.matchSessionOperations();
				break;
			case 5:
				paymentOperations.paymentOperations();
				break;
			case 6:
				trainingSessionOperations.trainingSessionOperations();
				break;
			case 7:
				sportOperations.sportOperations();
				break;
			case 0:
				System.out.println("Exiting...");
				sc.close();
				return;
			default:
				System.out.println("Invalid option, please try again.");
			}
		}
	}
}
