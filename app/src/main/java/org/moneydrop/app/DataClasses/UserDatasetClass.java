package org.moneydrop.app.DataClasses;

public class UserDatasetClass {
    String name;
    String email;
    String phone;
    String balance;
    String emailv;

    public UserDatasetClass() {
    }

    public UserDatasetClass(String name, String email, String phone, String balance, String emailv) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.balance = balance;
        this.emailv = emailv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getEmailv() {
        return emailv;
    }

    public void setEmailv(String emailv) {
        this.emailv = emailv;
    }
}
