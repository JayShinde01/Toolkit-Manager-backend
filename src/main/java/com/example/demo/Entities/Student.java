
package com.example.demo.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends User {

    private String branch;

    private String division;

    private String prn;

    private Integer year;

    // getters & setters

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getPrn() {
        return prn;
    }

    public void setPrn(String prn) {
        this.prn = prn;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Student() {
        super();
    }

    public Student(Integer id, String userName, String password,
                   com.example.demo.enums.UserRole role,
                   String email, String phone,
                   String branch, String division,
                   String prn, Integer year) {
        super(id, userName, password, role, email, phone);
        this.branch = branch;
        this.division = division;
        this.prn = prn;
        this.year = year;
    }
}