package com.demo.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "coaches")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 2, max = 13)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(min = 2, max = 15)
    @Column(name = "specialization", nullable = false)
    private String specialization;

    @NotNull
    @Email
    @Size(min = 5, max = 20)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(min = 10, max = 12)
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotNull
    @Column(name = "hire_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date hireDate;

    @NotNull
    @Min(0)
    @Column(name = "experience", nullable = false)
    private Integer experience;

    @Size(max = 50)
    @Column(name = "certifications")
    private String certifications;

    @OneToOne(mappedBy = "coach")
    private Batch batch;

    @OneToOne(mappedBy = "coach")
    private TrainingSession trainingSession;

    @OneToOne(mappedBy = "coach")
    private MatchSession matchSession;
}
