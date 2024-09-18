package com.demo.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	@Column(name = "payment_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date paymentDate;
	@NotNull
	@Min(0)
	@Column(name = "amount", nullable = false)
	private Double amount;
	@NotNull
	@Size(min = 2, max = 10)
	@Column(name = "payment_method", nullable = false)
	private String paymentMethod;
	@NotNull
	@Size(min = 2, max = 10)
	@Column(name = "status", nullable = false)
	private String status;

	@OneToOne
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;
	@Size(max = 25)
	@Column(name = "description")
	private String description;
	// Getters and Setters

}
