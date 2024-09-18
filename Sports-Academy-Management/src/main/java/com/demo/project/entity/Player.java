package com.demo.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Player {
	@Id
	private int pid;
	
	private String pname;
	
	private long pno;
	
	private String paddress;
	
	private int jerseryno;
	
	private String location;
	
	private String role;
	
	private int age;

}
