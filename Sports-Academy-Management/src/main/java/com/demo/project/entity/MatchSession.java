package com.demo.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "match_sessions")
public class MatchSession {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int mid;

	@NotNull
	@Column(name = "match_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date matchDate;

	@NotNull
	@Column(name = "start_time", nullable = false)
	private String startTime;

	@NotNull
	@Column(name = "end_time", nullable = false)
	private String endTime;

	@NotNull
	@Size(min = 2, max = 10)
	@Column(name = "team_a", nullable = false)
	private String teamA;

	@NotNull
	@Size(min = 2, max = 10)
	@Column(name = "team_b", nullable = false)
	private String teamB;

	@Column(name = "score_a")
	private Integer scoreA;

	@Column(name = "score_b")
	private Integer scoreB;

	@NotNull
	@Size(min = 2, max = 10)
	@Column(name = "location", nullable = false)
	private String location;

	@ManyToOne
	@JoinColumn(name = "sport_id", nullable = false)
	private Sport sport;

	@OneToOne
	@JoinColumn(name = "coach_id", nullable = false)
	private Coach coach;
}
