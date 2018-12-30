package com.example.virus.saver.Auth.Model;

public class SaveRegisteredDetails
{
    public String email;

    public String password;

    public SaveRegisteredDetails() {
    }

    public SaveRegisteredDetails(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
