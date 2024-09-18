package com.demo.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Team {

	@Id
	private int tid;
	
	private String tname;
	
	private long tphno;
	
	private String tcapname;
	
	private String tvcapname;
	
	private String tgame;
}
