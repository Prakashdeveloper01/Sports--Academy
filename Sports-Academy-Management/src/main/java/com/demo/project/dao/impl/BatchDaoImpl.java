package com.demo.project.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.demo.project.entity.Batch;
import com.demo.project.entity.Coach;
import com.demo.project.entity.Sport;
import com.demo.project.dao.BatchesDao;
import com.demo.project.helper.HibernateHelper;

import java.util.List;
import java.util.Scanner;

public class BatchDaoImpl implements BatchesDao {
	private Scanner sc = new Scanner(System.in);

	@Override
	public void save() {
		Transaction transaction = null;
		try (Session session = HibernateHelper.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			System.out.println("Enter batch name:");
			String name = sc.nextLine();

			System.out.println("Enter start time:");
			String startTime = sc.nextLine();

			System.out.println("Enter end time:");
			String endTime = sc.nextLine();

			List<Sport> sports = session.createQuery("FROM Sport", Sport.class).list();
			System.out.println("Available Sports:");
			for (Sport sport : sports) {
				System.out.println("ID: " + sport.getId() + ", Name: " + sport.getName());
			}
			System.out.println("Enter sport ID:");
			int sportId = sc.nextInt();
			// sc.nextLine(); // consume newline

			List<Coach> coachs = session.createQuery("FROM Coach", Coach.class).list();
			System.out.println("Available C:");
			for (Coach coach : coachs) {
				System.out.println("ID: " + coach.getId() + ", Name: " + coach.getName());
			}

			System.out.println("Enter Coach ID:");
			int coachId = sc.nextInt();
			// sc.nextLine(); // consume newline

			Coach coach = session.get(Coach.class, coachId);
			Sport sport = session.get(Sport.class, sportId);

			Batch batch = new Batch();
			batch.setName(name);
			batch.setStartTime(startTime);
			batch.setEndTime(endTime);
			batch.setSport(sport);
			batch.setCoach(coach);

			session.save(batch);
			transaction.commit();
			System.out.println("Batch added successfully.");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		Transaction transaction = null;
		try (Session session = HibernateHelper.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			System.out.println("Enter batch ID:");
			int id = sc.nextInt();
			sc.nextLine(); // consume newline

			Batch batch = session.get(Batch.class, id);
			if (batch != null) {
				System.out.println("What details do you want to modify?");
				System.out.println("1. Name");
				System.out.println("2. Start Time");
				System.out.println("3. End Time");
				int choice = sc.nextInt();
				sc.nextLine(); // consume newline
				switch (choice) {
				case 1:
					System.out.println("Enter updated name:");
					String name = sc.nextLine();
					batch.setName(name);
					break;
				case 2:
					System.out.println("Enter updated start time:");
					String startTime = sc.nextLine();
					batch.setStartTime(startTime);
					break;
				case 3:
					System.out.println("Enter updated end time:");
					String endTime = sc.nextLine();
					batch.setEndTime(endTime);
					break;
				default:
					System.out.println("Invalid choice.");
					transaction.rollback();
					return;
				}
				session.update(batch);
				transaction.commit();
				System.out.println("Batch updated successfully.");
			} else {
				System.out.println("No such batch exists.");
				transaction.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	@Override
	public Batch findById() {
		try (Session session = HibernateHelper.getSessionFactory().openSession()) {
			System.out.println("Enter batch ID:");
			int id = sc.nextInt();
			sc.nextLine(); // consume newline

			Batch batch = session.get(Batch.class, id);
			if (batch != null) {
				System.out.println("\tId : " + batch.getId() + " " + "\n\tName : " + batch.getName() + " "
						+ "\n\tStartTime : " + batch.getStartTime() + " " + "\n\tEndTime : " + batch.getEndTime()
						+ "\n\tSport : " + (batch.getSport() != null ? batch.getSport().getName() : "N/A")
						+ "\n\tCoach : " + (batch.getCoach() != null ? batch.getCoach().getName() : "N/A"));
			} else {
				System.out.println("No batch found with ID: " + id);
			}
			return batch;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Batch> findAll() {
		try (Session session = HibernateHelper.getSessionFactory().openSession()) {
			Query<Batch> query = session.createQuery("FROM Batch", Batch.class);
			List<Batch> batches = query.list();
			if (batches.isEmpty()) {
				System.out.println("No batches found.");
			} else {
				for (Batch batch : batches) {
					System.out.println("\tId : " + batch.getId() + " " + "\n\tName : " + batch.getName() + " "
							+ "\n\tStartTime : " + batch.getStartTime() + " " + "\n\tEndTime : " + batch.getEndTime()
							+ "\n\tSport : " + (batch.getSport() != null ? batch.getSport().getName() : "N/A")
							+ "\n\tCoach : " + (batch.getCoach() != null ? batch.getCoach().getName() : "N/A"));
				}
			}
			return batches;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
