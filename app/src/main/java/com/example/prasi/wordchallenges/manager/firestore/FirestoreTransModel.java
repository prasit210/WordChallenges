package com.example.prasi.wordchallenges.manager.firestore;

public class FirestoreTransModel {
   private String eng,th,type,daterecord;
   private int id;
    public FirestoreTransModel() {
    }

    public FirestoreTransModel(Integer id, String eng, String th, String type, String daterecord) {
        this.id = id;
        this.eng = eng;
        this.th = th;
        this.type = type;
        this.daterecord = daterecord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getTh() {
        return th;
    }

    public void setTh(String th) {
        this.th = th;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDaterecord() {
        return daterecord;
    }

    public void setDaterecord(String daterecord) {
        this.daterecord = daterecord;
    }
}
