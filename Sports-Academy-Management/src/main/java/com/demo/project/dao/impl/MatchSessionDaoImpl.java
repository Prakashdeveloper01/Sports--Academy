package com.demo.project.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.demo.project.entity.MatchSession;
import com.demo.project.entity.Sport;
import com.demo.project.entity.Coach;
import com.demo.project.dao.MatchSessionDao;
import com.demo.project.helper.HibernateHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MatchSessionDaoImpl implements MatchSessionDao {
	private Scanner sc = new Scanner(System.in);

	@Override
	public void save() {
		Transaction transaction = null;
		try (Session session = HibernateHelper.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			System.out.println("Enter match date (yyyy-mm-dd):");
			String matchDateStr = sc.nextLine();

			System.out.println("Enter start time:");
			String startTime = sc.nextLine();

			System.out.println("Enter end time:");
			String endTime = sc.nextLine();

			System.out.println("Enter team A:");
			String teamA = sc.nextLine();

			System.out.println("Enter team B:");
			String teamB = sc.nextLine();

			System.out.println("Enter score A:");
			Integer scoreA = sc.nextInt();

			System.out.println("Enter score B:");
			Integer scoreB = sc.nextInt();
			sc.nextLine(); // consume newline

			System.out.println("Enter location:");
			String location = sc.nextLine();

			List<Sport> sports = session.createQuery("FROM Sport", Sport.class).list();
			System.out.println("Available Sports:");
			for (Sport sport : sports) {
				System.out.println("ID: " + sport.getId() + ", Name: " + sport.getName());
			}
			System.out.println("Enter Sport ID:");
			int sportId = sc.nextInt();
			sc.nextLine(); // consume newline

			Sport sport = session.get(Sport.class, sportId);
			if (sport == null) {
				System.out.println("Sport not found.");
				return;
			}

			List<Coach> coaches = session.createQuery("FROM Coach", Coach.class).list();
			System.out.println("Available Coaches:");
			for (Coach coach : coaches) {
				System.out.println("ID: " + coach.getId() + ", Name: " + coach.getName());
			}
			System.out.println("Enter Coach ID:");
			int coachId = sc.nextInt();
			sc.nextLine(); // consume newline

			Coach coach = session.get(Coach.class, coachId);
			if (coach == null) {
				System.out.println("Coach not found.");
				return;
			}

			MatchSession matchSession = new MatchSession();
			matchSession.setMatchDate(parseDate(matchDateStr));
			matchSession.setStartTime(startTime);
			matchSession.setEndTime(endTime);
			matchSession.setTeamA(teamA);
			matchSession.setTeamB(teamB);
			matchSession.setScoreA(scoreA);
			matchSession.setScoreB(scoreB);
			matchSession.setLocation(location);
			matchSession.setSport(sport);
			matchSession.setCoach(coach);

			session.save(matchSession);
			transaction.commit();
			System.out.println("Match session added successfully.");
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

			System.out.println("Enter match session ID:");
			int id = sc.nextInt();
			sc.nextLine(); // consume newline

			MatchSession matchSession = session.get(MatchSession.class, id);
			if (matchSession != null) {
				System.out.println("What details do you want to modify?");
				System.out.println("1. Match Date");
				System.out.println("2. Start Time");
				System.out.println("3. End Time");
				System.out.println("4. Team A");
				System.out.println("5. Team B");
				System.out.println("6. Score A");
				System.out.println("7. Score B");
				System.out.println("8. Location");
				int choice = sc.nextInt();
				sc.nextLine(); // consume newline

				switch (choice) {
				case 1:
					System.out.println("Enter updated match date (yyyy-mm-dd):");
					String matchDate = sc.nextLine();
					matchSession.setMatchDate(java.sql.Date.valueOf(matchDate));
					break;
				case 2:
					System.out.println("Enter updated start time:");
					String startTime = sc.nextLine();
					matchSession.setStartTime(startTime);
					break;
				case 3:
					System.out.println("Enter updated end time:");
					String endTime = sc.nextLine();
					matchSession.setEndTime(endTime);
					break;
				case 4:
					System.out.println("Enter updated team A:");
					String teamA = sc.nextLine();
					matchSession.setTeamA(teamA);
					break;
				case 5:
					System.out.println("Enter updated team B:");
					String teamB = sc.nextLine();
					matchSession.setTeamB(teamB);
					break;
				case 6:
					System.out.println("Enter updated score A:");
					int scoreA = sc.nextInt();
					matchSession.setScoreA(scoreA);
					break;
				case 7:
					System.out.println("Enter updated score B:");
					int scoreB = sc.nextInt();
					matchSession.setScoreB(scoreB);
					break;
				case 8:
					System.out.println("Enter updated location:");
					String location = sc.nextLine();
					matchSession.setLocation(location);
					break;
				default:
					System.out.println("Invalid choice.");
					transaction.rollback();
					return;
				}
				session.update(matchSession);
				transaction.commit();
				System.out.println("Match session updated successfully.");
			} else {
				System.out.println("No such match session exists.");
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
	public MatchSession findById() {
		try (Session session = HibernateHelper.getSessionFactory().openSession()) {
			System.out.println("Enter match session ID:");
			int id = sc.nextInt();
			sc.nextLine(); // consume newline

			MatchSession matchSession = session.get(MatchSession.class, id);
			if (matchSession != null) {
				System.out.println("\n\tId : " + matchSession.getMid() + " " + "\n\tMatchDate : "
						+ matchSession.getMatchDate() + " " + "\n\tStartTime : " + matchSession.getStartTime() + " "
						+ "\n\tEndTime : " + matchSession.getEndTime() + " " + "\n\tTeamA : " + matchSession.getTeamA()
						+ " " + "\n\tTeamB : " + matchSession.getTeamB() + " " + "\n\tScoreA : "
						+ matchSession.getScoreA() + " " + "\n\tScoreB : " + matchSession.getScoreB() + " "
						+ "\n\tLocation : " + matchSession.getLocation());
			} else {
				System.out.println("No match session found with ID: " + id);
			}
			return matchSession;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<MatchSession> findAll() {
		try (Session session = HibernateHelper.getSessionFactory().openSession()) {
			Query<MatchSession> query = session.createQuery("FROM MatchSession", MatchSession.class);
			List<MatchSession> matchSessions = query.list();
			if (matchSessions.isEmpty()) {
				System.out.println("No match sessions found.");
			} else {
				for (MatchSession matchSession : matchSessions) {
					System.out.println("\n\tId : " + matchSession.getMid() + " " + "\n\tMatchDate : "
							+ matchSession.getMatchDate() + " " + "\n\tStartTime : " + matchSession.getStartTime() + " "
							+ "\n\tEndTime : " + matchSession.getEndTime() + " " + "\n\tTeamA : "
							+ matchSession.getTeamA() + " " + "\n\tTeamB : " + matchSession.getTeamB() + " "
							+ "\n\tScoreA : " + matchSession.getScoreA() + " " + "\n\tScoreB : "
							+ matchSession.getScoreB() + " " + "\n\tLocation : " + matchSession.getLocation());
				}
			}
			return matchSessions;
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
