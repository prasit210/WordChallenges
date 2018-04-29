package com.example.prasi.wordchallenges.manager.firestore;

public class FirestorePersonalModel {
    private String password, name, lastname ,tel ,coutry ,dateregister = "";

    public FirestorePersonalModel() {
    }

    public FirestorePersonalModel(String password, String name, String lastname, String tel, String coutry, String dateregister) {
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.tel = tel;
        this.coutry = coutry;
        this.dateregister = dateregister;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCoutry() {
        return coutry;
    }

    public void setCoutry(String coutry) {
        this.coutry = coutry;
    }

    public String getDateregister() {
        return dateregister;
    }

    public void setDateregister(String dateregister) {
        this.dateregister = dateregister;
    }
}

