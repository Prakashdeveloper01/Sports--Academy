package com.demo.project.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.demo.project.entity.Payment;
import com.demo.project.entity.Student;
import com.demo.project.dao.PaymentDao;
import com.demo.project.helper.HibernateHelper;

import java.util.List;
import java.util.Scanner;

public class PaymentDaoImpl implements PaymentDao {
    private Scanner sc = new Scanner(System.in);

    @Override
    public void save() {
        Transaction transaction = null;
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            System.out.println("Enter payment date (yyyy-mm-dd):");
            String paymentDate = sc.nextLine();

            System.out.println("Enter amount:");
            double amount = sc.nextDouble();
            sc.nextLine(); // consume newline

            System.out.println("Enter payment method:");
            String paymentMethod = sc.nextLine();

            System.out.println("Enter status:");
            String status = sc.nextLine();

            System.out.println("Enter student ID:");
            int studentId = sc.nextInt();
            sc.nextLine(); // consume newline

            Student student = session.get(Student.class, studentId);
            if (student == null) {
                System.out.println("Invalid student ID.");
                transaction.rollback();
                return;
            }

            Payment payment = new Payment();
            payment.setPaymentDate(java.sql.Date.valueOf(paymentDate));
            payment.setAmount(amount);
            payment.setPaymentMethod(paymentMethod);
            payment.setStatus(status);
            payment.setStudent(student);

            session.save(payment);
            transaction.commit();
            System.out.println("Payment added successfully.");
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

            System.out.println("Enter payment ID:");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline

            Payment payment = session.get(Payment.class, id);
            if (payment != null) {
                System.out.println("What details do you want to modify?");
                System.out.println("1. Payment Date");
                System.out.println("2. Amount");
                System.out.println("3. Payment Method");
                System.out.println("4. Status");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.println("Enter updated payment date (yyyy-mm-dd):");
                        String paymentDate = sc.nextLine();
                        payment.setPaymentDate(java.sql.Date.valueOf(paymentDate));
                        break;
                    case 2:
                        System.out.println("Enter updated amount:");
                        double amount = sc.nextDouble();
                        sc.nextLine(); // consume newline
                        payment.setAmount(amount);
                        break;
                    case 3:
                        System.out.println("Enter updated payment method:");
                        String paymentMethod = sc.nextLine();
                        payment.setPaymentMethod(paymentMethod);
                        break;
                    case 4:
                        System.out.println("Enter updated status:");
                        String status = sc.nextLine();
                        payment.setStatus(status);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        transaction.rollback();
                        return;
                }
                session.update(payment);
                transaction.commit();
                System.out.println("Payment updated successfully.");
            } else {
                System.out.println("No such payment exists.");
                transaction.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) transaction.rollback();
        }
    }

    @Override
    public Payment findById() {
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            System.out.println("Enter payment ID:");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline

            Payment payment = session.get(Payment.class, id);
            if (payment != null) {
                String studentName = payment.getStudent() != null ? payment.getStudent().getName() : "Unknown";
                System.out.println("\n\tId : "+payment.getId() + " " +"\n\tPaymentDate : "+ payment.getPaymentDate() + " " +
                       "\n\tAmount : "+ payment.getAmount() + " " +"\n\tPaymentMethod : "+ payment.getPaymentMethod() + " " +
                       "\n\tStatus : "+ payment.getStatus() + " " + "\n\tStudentName : "+studentName);
            } else {
                System.out.println("No payment found with ID: " + id);
            }
            return payment;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Payment> findAll() {
        try (Session session = HibernateHelper.getSessionFactory().openSession()) {
            Query<Payment> query = session.createQuery("FROM Payment", Payment.class);
            List<Payment> payments = query.list();
            if (payments.isEmpty()) {
                System.out.println("No payments found.");
            } else {
                for (Payment payment : payments) {
                    String studentName = payment.getStudent() != null ? payment.getStudent().getName() : "Unknown";
                    System.out.println(payment.getId() + " " + payment.getPaymentDate() + " " +
                            payment.getAmount() + " " + payment.getPaymentMethod() + " " +
                            payment.getStatus() + " " + studentName);
                }
            }
            return payments;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
