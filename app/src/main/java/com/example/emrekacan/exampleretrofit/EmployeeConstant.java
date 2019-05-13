package com.example.emrekacan.exampleretrofit;

import java.util.List;

public class EmployeeConstant {

    private String name;
    private int age;
    private List<String> job;
    private Boolean iswork;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getJob() {
        return job;
    }

    public void setJob(List<String> job) {
        this.job = job;
    }

    public Boolean getIswork() {
        return iswork;
    }

    public void setIswork(Boolean iswork) {
        this.iswork = iswork;
    }
}
