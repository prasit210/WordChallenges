package com.example.prasi.wordchallenges.manager.firestore;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class FirestoreManager extends FirestorePersonalModel {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private FirebaseFirestore firestore;
    private FirestorePersonalModel firestorePersonalModel;
    private Date date;
    private SimpleDateFormat simpleDateFormat;
    private Timestamp timestamp;
    private AtomicInteger atomicInteger;
    private String str1,time;
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
        personRegister.put("logintype","Guest");
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

    public String RegisterInFirestoreWithFacebook(String email,String name,String lastname,String id,String time){
        firestore = FirebaseFirestore.getInstance();
        Map<String,Object> personRegister = new HashMap<>();
        personRegister.put("logintype","Facebook");
        personRegister.put("email",email);
        personRegister.put("name",name);
        personRegister.put("lastname",lastname);
        personRegister.put("id",id);
        personRegister.put("dateregister",time);
        firestore.collection("WordChallenge").document("Users").collection(email).document("PersonDetail").set(personRegister, SetOptions.merge());
        return null;
    }

    public String addTextCollaction(String eng,String th,String email,String type,Context context){
        sharedPreferences = context.getSharedPreferences("ADD",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        firestore = FirebaseFirestore.getInstance();

        date = Calendar.getInstance().getTime();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dates = simpleDateFormat.format(date);
        editor.putString("Date",dates);

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
        time = format.format(date);

        if(firestore != null){
            //นับจำนวนว่าครบ 10 แล้วหรือยัง?
            int count_add = sharedPreferences.getInt("ID",1);
            if(count_add == 11){
                Toast.makeText(context,"Full",Toast.LENGTH_SHORT).show();
                editor.putString("Status","1");
                editor.commit();
            }else if (count_add < 11){
                Map<String,Object> text = new HashMap<>();
                text.put("id",count_add);
                text.put("eng",eng);
                text.put("th",th);
                text.put("type",type);
                text.put("daterecord",time);
                firestore.collection("WordChallenge").document("Users").collection(email).document("Texttotrans").collection(dates).document(String.valueOf(count_add)).set(text,SetOptions.merge());
                editor.putInt("ID",++count_add);
                editor.putString("Status","0");
                editor.commit();
                Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();
            }

        }
        return null;
    }


}
