package com.demo.project.entity;

import jakarta.persistence.*;
import java.util.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sports")
public class Sport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 2, max = 10)
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 25)
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "sport")
    private List<Student> students;

    @OneToMany(mappedBy = "sport")
    private List<MatchSession> matchSessions;
}
