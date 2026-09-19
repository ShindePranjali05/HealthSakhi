package com.example.healthsakhi.room;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String email;
    public String password;
    public int height;
    public int weight;
    public int age;


    @Ignore
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Full constructor
    public User(String name, String email, String password, int height, int weight, int age) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.height = height;
        this.weight = weight;
        this.age = age;
    }

    // Constructor for login or minimal signup

}
