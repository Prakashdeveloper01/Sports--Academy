package com.demo.project.dao.impl;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.demo.project.entity.Coach;
import com.demo.project.dao.CoachDao;
import com.demo.project.helper.HibernateHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CoachDaoImpl implements CoachDao {
    private Scanner sc = new Scanner(System.in);

    @Override
    public void save() {
        Transaction transaction = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            System.out.println("Enter Coach Name:");
            String name = sc.nextLine();

            System.out.println("Enter Specialization:");
            String specialization = sc.nextLine();

            System.out.println("Enter Email:");
            String email = sc.nextLine();

            System.out.println("Enter Phone Number:");
            String phoneNumber = sc.nextLine();

            System.out.println("Enter Hire Date (yyyy-mm-dd):");
            String hireDateStr = sc.nextLine();
            Date hireDate = parseDate(hireDateStr);

            System.out.println("Enter Experience:");
            int experience = sc.nextInt();
            sc.nextLine(); // consume newline

            System.out.println("Enter Certifications (Zonal / State / District):");
            String certifications = sc.nextLine();

            Coach coach = new Coach();
            coach.setName(name);
            coach.setSpecialization(specialization);
            coach.setEmail(email);
            coach.setPhoneNumber(phoneNumber);
            coach.setHireDate(new java.sql.Date(hireDate.getTime()));
            coach.setExperience(experience);
            coach.setCertifications(certifications);

            session.save(coach);
            transaction.commit();
            System.out.println("Coach added successfully.");
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

            System.out.println("Enter coach ID:");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline

            Coach coach = session.get(Coach.class, id);
            if (coach != null) {
                System.out.println("What details do you want to modify?");
                System.out.println("1. Name");
                System.out.println("2. Specialization");
                System.out.println("3. Email");
                System.out.println("4. Phone Number");
                System.out.println("5. Hire Date");
                System.out.println("6. Experience");
                System.out.println("7. Certifications");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline
                switch (choice) {
                    case 1:
                        System.out.println("Enter Updated Name:");
                        coach.setName(sc.nextLine());
                        break;
                    case 2:
                        System.out.println("Enter Updated Specialization:");
                        coach.setSpecialization(sc.nextLine());
                        break;
                    case 3:
                        System.out.println("Enter Updated Email:");
                        coach.setEmail(sc.nextLine());
                        break;
                    case 4:
                        System.out.println("Enter Updated Phone Number:");
                        coach.setPhoneNumber(sc.nextLine());
                        break;
                    case 5:
                        System.out.println("Enter Updated Hire Date (yyyy-mm-dd):");
                        coach.setHireDate(new java.sql.Date(parseDate(sc.nextLine()).getTime()));
                        break;
                    case 6:
                        System.out.println("Enter Updated Experience:");
                        coach.setExperience(sc.nextInt());
                        sc.nextLine(); // consume newline
                        break;
                    case 7:
                        System.out.println("Enter Updated Certifications:");
                        coach.setCertifications(sc.nextLine());
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        transaction.rollback();
                        return;
                }
                session.update(coach);
                transaction.commit();
                System.out.println("Coach Updated Successfully.");
            } else {
                System.out.println("No such coach exists.");
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Coach findById() {
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            System.out.println("Enter coach ID:");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline

            Coach coach = session.get(Coach.class, id);
            if (coach != null) {
                System.out.println("\tId : " + coach.getId() + " " +
                        "\n\tName : " + coach.getName() + " " +
                        "\n\tSpecialization : " + coach.getSpecialization() + " " +
                        "\n\tEmail : " + coach.getEmail() + " " +
                        "\n\tPhone Number : " + coach.getPhoneNumber() + " " +
                        "\n\tHire Date : " + coach.getHireDate() + " " +
                        "\n\tExperience : " + coach.getExperience() + " " +
                        "\n\tCertifications : " + coach.getCertifications());
            } else {
                System.out.println("No coach found with ID: " + id);
            }
            return coach;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Coach> findAll() {
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            Query<Coach> query = session.createQuery("FROM Coach", Coach.class);
            List<Coach> coaches = query.list();
            if (coaches.isEmpty()) {
                System.out.println("No coach found.");
            } else {
                for (Coach coach : coaches) {
                    System.out.println("\tId : " + coach.getId() + " " + "\n\tName : " + coach.getName() + " "
                            + "\n\tSpecialization : " + coach.getSpecialization() + " " + "\n\tEmail : "
                            + coach.getEmail() + " " + "\n\tPhoneNumber :" + coach.getPhoneNumber() + " "
                            + "\n\tHireDate : " + coach.getHireDate() + " " + "\n\tExperience : "
                            + coach.getExperience() + " " + "\n\tCertification : " + coach.getCertifications());
                }
            }
            return coaches;
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
            e.printStackTrace();
            return null;
        }
    }
}
