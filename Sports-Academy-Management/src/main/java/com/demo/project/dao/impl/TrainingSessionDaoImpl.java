package com.demo.project.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.demo.project.entity.Batch;
import com.demo.project.entity.Coach;
import com.demo.project.entity.TrainingSession;
import com.demo.project.dao.TrainingSessionDao;
import com.demo.project.helper.HibernateHelper;

import java.util.List;
import java.util.Scanner;

public class TrainingSessionDaoImpl implements TrainingSessionDao {
    private Scanner sc = new Scanner(System.in);

    @Override
    public void save() {
        Transaction transaction = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Input session details
            System.out.println("Enter session date (yyyy-mm-dd):");
            String sessionDate = sc.nextLine();

            System.out.println("Enter start time:");
            String startTime = sc.nextLine();

            System.out.println("Enter end time:");
            String endTime = sc.nextLine();

            System.out.println("Enter feedback:");
            String feedback = sc.nextLine();

            System.out.println("Enter location:");
            String location = sc.nextLine();

            System.out.println("Enter Coach ID:");
            int coachId = sc.nextInt();
            sc.nextLine(); // consume newline

            // List available batches
            List<Batch> batches = session.createQuery("FROM Batch", Batch.class).list();
            System.out.println("Available Batches:");
            for (Batch batch : batches) {
                System.out.println("ID: " + batch.getId() + ", Name: " + batch.getName() + 
                                   ", Start Time: " + batch.getStartTime() + ", End Time: " + batch.getEndTime());
            }
            System.out.println("Enter batch ID:");
            int batchId = sc.nextInt();
            sc.nextLine(); // consume newline

            // Fetch coach and batch
            Coach coach = session.get(Coach.class, coachId);
            Batch batch = session.get(Batch.class, batchId);

            if (coach == null || batch == null) {
                System.out.println("Invalid coach or batch ID.");
                transaction.rollback();
                return;
            }

            // Create and save TrainingSession
            TrainingSession trainingSession = new TrainingSession();
            trainingSession.setSessionDate(java.sql.Date.valueOf(sessionDate));
            trainingSession.setStartTime(startTime);
            trainingSession.setEndTime(endTime);
            trainingSession.setFeedback(feedback);
            trainingSession.setLocation(location);
            trainingSession.setCoach(coach);
            trainingSession.setBatch(batch);

            session.save(trainingSession);
            transaction.commit();
            System.out.println("Training session added successfully.");
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

            System.out.println("Enter training session ID:");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline

            TrainingSession trainingSession = session.get(TrainingSession.class, id);
            if (trainingSession != null) {
                System.out.println("What details do you want to modify?");
                System.out.println("1. Session Date");
                System.out.println("2. Start Time");
                System.out.println("3. End Time");
                System.out.println("4. Feedback");
                System.out.println("5. Location");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.println("Enter updated session date (yyyy-mm-dd):");
                        String sessionDate = sc.nextLine();
                        trainingSession.setSessionDate(java.sql.Date.valueOf(sessionDate));
                        break;
                    case 2:
                        System.out.println("Enter updated start time:");
                        String startTime = sc.nextLine();
                        trainingSession.setStartTime(startTime);
                        break;
                    case 3:
                        System.out.println("Enter updated end time:");
                        String endTime = sc.nextLine();
                        trainingSession.setEndTime(endTime);
                        break;
                    case 4:
                        System.out.println("Enter updated feedback:");
                        String feedback = sc.nextLine();
                        trainingSession.setFeedback(feedback);
                        break;
                    case 5:
                        System.out.println("Enter updated location:");
                        String location = sc.nextLine();
                        trainingSession.setLocation(location);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        transaction.rollback();
                        return;
                }
                session.update(trainingSession);
                transaction.commit();
                System.out.println("Training session updated successfully.");
            } else {
                System.out.println("No such training session exists.");
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public TrainingSession findById() {
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            System.out.println("Enter training session ID:");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline

            TrainingSession trainingSession = session.get(TrainingSession.class, id);
            if (trainingSession != null) {
                System.out.println("ID: " + trainingSession.getId() + ", Date: " + trainingSession.getSessionDate() + 
                                   ", Start Time: " + trainingSession.getStartTime() + ", End Time: " + trainingSession.getEndTime() + 
                                   ", Feedback: " + trainingSession.getFeedback() + ", Location: " + trainingSession.getLocation());
            } else {
                System.out.println("No training session found with ID: " + id);
            }
            return trainingSession;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TrainingSession> findAll() {
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            Query<TrainingSession> query = session.createQuery("FROM TrainingSession", TrainingSession.class);
            List<TrainingSession> trainingSessions = query.list();
            if (trainingSessions.isEmpty()) {
                System.out.println("No training sessions found.");
            } else {
                for (TrainingSession trainingSession : trainingSessions) {
                    System.out.println("ID: " + trainingSession.getId() + ", Date: " + trainingSession.getSessionDate() + 
                                       ", Start Time: " + trainingSession.getStartTime() + ", End Time: " + trainingSession.getEndTime() + 
                                       ", Feedback: " + trainingSession.getFeedback() + ", Location: " + trainingSession.getLocation());
                }
            }
            return trainingSessions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
