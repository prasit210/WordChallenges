package com.example.prasi.wordchallenges.manager.firestore;

import android.content.SharedPreferences;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class FirestoreManager extends FirestoreModel{
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private FirebaseFirestore firestore;
    private FirestoreModel firestoreModel;
    private Date date;
    private SimpleDateFormat simpleDateFormat;
    private Timestamp timestamp;
    private AtomicInteger atomicInteger;
    private String str1;
    private String str2;
    private String email;
    private String password;
    private String dates;
    private int id;

    public FirestoreManager() {

    }

    public FirestoreManager(String str1, String str2) {
        this.str1 = str1;
        this.str2 = str2;
    }

    public String RegisterInFirestore(String email,String password,String name,String lastname,String tel,String coutry,String time){
        firestore = FirebaseFirestore.getInstance();
        Map<String,Object> personRegister = new HashMap<>();
        personRegister.put("email",email);
        personRegister.put("password",password);
        personRegister.put("name",name);
        personRegister.put("lastname",lastname);
        personRegister.put("tel",tel);
        personRegister.put("coutry",coutry);
        personRegister.put("dateregister",time);
        firestore.collection("WordChallenge").document("Users").collection(email).document("PersonDetail").set(personRegister, SetOptions.merge());
        return null;
    }

}
