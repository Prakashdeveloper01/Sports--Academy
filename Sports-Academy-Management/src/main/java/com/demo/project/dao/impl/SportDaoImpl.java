package com.demo.project.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.demo.project.entity.Sport;
import com.demo.project.dao.SportDao;
import com.demo.project.helper.HibernateHelper;

import java.util.List;
import java.util.Scanner;

public class SportDaoImpl implements SportDao {
    private Scanner sc = new Scanner(System.in);

    @Override
    public void save() {
        Transaction transaction = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            System.out.println("Enter sport name:");
            String name = sc.nextLine();

            System.out.println("Enter Description:");
            String description = sc.nextLine();

            Sport sport = new Sport();
            sport.setName(name);
            sport.setDescription(description);

            session.save(sport);
            transaction.commit();
            System.out.println("Sport added successfully.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        Transaction transaction = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            System.out.println("Enter sport ID:");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline

            Sport sport = session.get(Sport.class, id);
            if (sport != null) {
                System.out.println("What details do you want to modify?");
                System.out.println("1. Name");
                System.out.println("2. Type");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline
                switch (choice) {
                    case 1:
                        System.out.println("Enter updated name:");
                        String name = sc.nextLine();
                        sport.setName(name);
                        break;
                    case 2:
                        System.out.println("Enter updated type:");
                        String description = sc.nextLine();
                        sport.setDescription(description);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        transaction.rollback();
                        return;
                }
                session.update(sport);
                transaction.commit();
                System.out.println("Sport updated successfully.");
            } else {
                System.out.println("No such sport exists.");
                transaction.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Sport findById() {
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            System.out.println("Enter sport ID:");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline

            Sport sport = session.get(Sport.class, id);
            if (sport != null) {
                System.out.println(sport.getId() + " " + sport.getName() + " " + sport.getDescription());
            } else {
                System.out.println("No sport found with ID: " + id);
            }
            return sport;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Sport> findAll() {
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            Query<Sport> query = session.createQuery("FROM Sport", Sport.class);
            List<Sport> sports = query.list();
            if (sports.isEmpty()) {
                System.out.println("No sports found.");
            } else {
                for (Sport sport : sports) {
                    System.out.println(sport.getId() + " " + sport.getName() + " " + sport.getDescription());
                }
            }
            return sports;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
