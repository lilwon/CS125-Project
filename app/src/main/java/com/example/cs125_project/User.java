package com.example.cs125_project;

public class User {
    public String fullname, age, dob, email, password;

    public User(){

    }
    public User(String fullname, String age, String dob, String email, String password){
        this.fullname = fullname;
        this.age = age;
        this.dob = dob;
        this.email = email;
        this.password = password;
    }
}
