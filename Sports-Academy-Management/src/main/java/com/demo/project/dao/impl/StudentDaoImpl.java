package com.demo.project.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import com.demo.project.entity.*;
import com.demo.project.dao.StudentDao;
import com.demo.project.helper.HibernateHelper;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class StudentDaoImpl implements StudentDao {
	private Scanner sc = new Scanner(System.in);

	private ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
	private Validator validator = vf.getValidator();

	@Override
	public void saveStudent() {
		Transaction transaction = null;
		try (Session session = HibernateHelper.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			System.out.println("Enter student name:");
			String name = sc.nextLine();

			System.out.println("Enter email:");
			String email = sc.nextLine();

			System.out.println("Enter phone number:");
			String phone = sc.nextLine();

			System.out.println("Enter address:");
			String address = sc.nextLine();

			List<Sport> sports = session.createQuery("FROM Sport", Sport.class).list();
			System.out.println("Available Sports:");
			for (Sport sport : sports) {
				System.out.println("ID: " + sport.getId() + ", Name: " + sport.getName());
			}
			System.out.println("Enter sport ID:");
			int sportId = sc.nextInt();

			List<Batch> batches = session.createQuery("FROM Batch", Batch.class).list();
			System.out.println("Available Batches:");
			for (Batch batch : batches) {
				System.out.println("ID: " + batch.getId() + ", Name: " + batch.getName() + " Start Time:"
						+ batch.getStartTime() + ", End Time:" + batch.getEndTime());
			}
			System.out.println("Enter batch ID:");
			int batchId = sc.nextInt();

			Date enrollmentDate = LastDate.now();
			System.out.println("Enrollment Date is set to: " + enrollmentDate);

			Sport sport = session.get(Sport.class, sportId);
			Batch batch = session.get(Batch.class, batchId);

			if (sport == null || batch == null) {
				System.out.println("Invalid sport or batch ID.");
				transaction.rollback();
				return;
			}

			Student student = new Student();
			student.setName(name);
			student.setEmail(email);
			student.setPhoneNumber(phone);
			student.setEnrollmentDate(enrollmentDate);
			student.setAddress(address);
			student.setSport(sport);
			student.setBatch(batch);

			Set<ConstraintViolation<Student>> violations = validator.validate(student);
			if (violations.isEmpty()) {
				session.save(student);
				transaction.commit();
				System.out.println("Student details added successfully.");
				System.out.println("Your Student id : " + student.getId());
			} else {
				for (ConstraintViolation<Student> violation : violations) {
					System.out.println(violation.getMessage());
				}
				transaction.rollback();
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		Transaction transaction = null;
		try (Session session = HibernateHelper.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			System.out.println("Enter student ID:");
			int id = sc.nextInt();
			sc.nextLine(); // consume newline

			Student student = session.get(Student.class, id);
			if (student != null) {
				System.out.println("What details do you want to modify?");
				System.out.println("1. Name");
				System.out.println("2. Email");
				System.out.println("3. Phone Number");
				System.out.println("4. Address");
				System.out.println("5. Enrollment Date");
				System.out.println("6. Sport ID");
				System.out.println("7. Batch ID");
				int choice = sc.nextInt();
				sc.nextLine(); // consume newline

				switch (choice) {
				case 1:
					System.out.println("Enter updated name:");
					student.setName(sc.nextLine());
					break;
				case 2:
					System.out.println("Enter updated email:");
					student.setEmail(sc.nextLine());
					break;
				case 3:
					System.out.println("Enter updated phone number:");
					student.setPhoneNumber(sc.nextLine());
					break;
				case 4:
					System.out.println("Enter updated address:");
					student.setAddress(sc.nextLine());
					break;
				case 5:
					System.out.println("Enter updated enrollment date (yyyy-MM-dd):");
					String enrollmentDateStr = sc.nextLine();
					student.setEnrollmentDate(parseDate(enrollmentDateStr));
					break;
				case 6:
					// List and display all Sports
					List<Sport> sports = session.createQuery("FROM Sport", Sport.class).list();
					System.out.println("Available Sports:");
					for (Sport sport : sports) {
						System.out.println("ID: " + sport.getId() + ", Name: " + sport.getName());
					}
					System.out.println("Enter updated sport ID:");
					Sport sport = session.get(Sport.class, sc.nextInt());
					sc.nextLine(); // consume newline
					student.setSport(sport);
					break;
				case 7:
					// List and display all Batches
					List<Batch> batches = session.createQuery("FROM Batch", Batch.class).list();
					System.out.println("Available Batches:");
					for (Batch batch : batches) {
						System.out.println("ID: " + batch.getId() + ", Name: " + batch.getName());
					}
					System.out.println("Enter updated batch ID:");
					Batch batch = session.get(Batch.class, sc.nextInt());
					sc.nextLine(); // consume newline
					student.setBatch(batch);
					break;
				default:
					System.out.println("Invalid choice.");
					transaction.rollback();
					return;
				}
				session.update(student);
				transaction.commit();
				System.out.println("Student details updated successfully.");
			} else {
				System.out.println("No such student exists.");
				transaction.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Student findById() {
		try (Session session = HibernateHelper.getSessionFactory().openSession()) {
			System.out.println("Enter student ID:");
			int id = sc.nextInt();
			sc.nextLine(); // consume newline

			Student student = session.get(Student.class, id);
			if (student != null) {
				System.out.println("ID: " + student.getId());
				System.out.println("Name: " + student.getName());
				System.out.println("Email: " + student.getEmail());
				System.out.println("Phone Number: " + student.getPhoneNumber());
				System.out.println("Enrollment Date: " + student.getEnrollmentDate());
				System.out.println("Address: " + student.getAddress());
				System.out.println("Sport: " + (student.getSport() != null ? student.getSport().getName() : "N/A"));
				System.out.println("Batch: " + (student.getBatch() != null ? student.getBatch().getName() : "N/A"));
				System.out.println("Payment: " + (student.getPayment() != null ? student.getPayment().getId() : "N/A"));
			} else {
				System.out.println("No student found with ID: " + id);
			}
			return student;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Student> findAll() {
		try (Session session = HibernateHelper.getSessionFactory().openSession()) {
			Query<Student> query = session.createQuery("FROM Student", Student.class);
			List<Student> students = query.list();
			if (students.isEmpty()) {
				System.out.println("No students found.");
			} else {
				for (Student student : students) {
					System.out.println("ID: " + student.getId());
					System.out.println("Name: " + student.getName());
					System.out.println("Email: " + student.getEmail());
					System.out.println("Phone Number: " + student.getPhoneNumber());
					System.out.println("Enrollment Date: " + student.getEnrollmentDate());
					System.out.println("Address: " + student.getAddress());
					System.out.println("Sport: " + (student.getSport() != null ? student.getSport().getName() : "N/A"));
					System.out.println("Batch: " + (student.getBatch() != null ? student.getBatch().getName() : "N/A"));
					System.out.println(
							"Payment: " + (student.getPayment() != null ? student.getPayment().getId() : "N/A"));
					System.out.println("-----------------------------");
				}
			}
			return students;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Date parseDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			System.out.println("Invalid date format. Please use yyyy-MM-dd.");
			return null;
		}
	}
}
