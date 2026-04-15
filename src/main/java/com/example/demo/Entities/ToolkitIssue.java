package com.example.demo.Entities;

import java.time.LocalDate;

import com.example.demo.enums.IssueStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ToolkitIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Toolkit toolkit;

    private LocalDate issueDate;
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    private IssueStatus status; // ISSUED, RETURNED, PENDING

	

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public Toolkit getToolkit() {
		return toolkit;
	}



	public void setToolkit(Toolkit toolkit) {
		this.toolkit = toolkit;
	}



	public LocalDate getIssueDate() {
		return issueDate;
	}



	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}



	public LocalDate getReturnDate() {
		return returnDate;
	}



	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}



	public IssueStatus getStatus() {
		return status;
	}



	public void setStatus(IssueStatus status) {
		this.status = status;
	}



	public ToolkitIssue(Integer id, User user, Toolkit toolkit, LocalDate issueDate, LocalDate returnDate,
			IssueStatus status) {
		super();
		this.id = id;
		this.user = user;
		this.toolkit = toolkit;
		this.issueDate = issueDate;
		this.returnDate = returnDate;
		this.status = status;
	}



	public ToolkitIssue() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
