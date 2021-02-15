package com.example.cs125_project;

public class User {
    public String fullName, age, dob, email, password;

    public User(){

    }
    public User(String fullName, String age, String dob, String email, String password){
        this.fullName = fullName;
        this.age = age;
        this.dob = dob;
        this.email = email;
        this.password = password;
    }

}
