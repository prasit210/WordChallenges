package com.example.prasi.wordchallenges.fragment.AddFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.prasi.wordchallenges.R;
import com.example.prasi.wordchallenges.manager.firestore.FirestoreTransModel;
import com.example.prasi.wordchallenges.view.RecyclerAddwordTodayAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AddWordTodayFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button button;
    private String doc;
    private Boolean check_date;
    private FirebaseFirestore firebaseFirestore;
    private SharedPreferences preLogin,preAdd;
    private List<FirestoreTransModel> list;
    public AddWordTodayFragment() {
        super();
    }

    public static AddWordTodayFragment newInstance() {
        AddWordTodayFragment fragment = new AddWordTodayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_addwordtoday, container, false);
        initInstances(rootView);
        checkDateForShow();

        return rootView;
    }

    private void checkDateForShow(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String dates = simpleDateFormat.format(date);

        //Check วันที่ปัจจุบัน ว่าเป็น Null หรือไม่
        //ถ้าไม่เป็น Null ก็ไป Get Data จากวันที่นั้นมาโชว์ใน Recycler View

        firebaseFirestore.collection("WordChallenge")
                .document("Users")
                .collection(preLogin.getString("Email",""))
                .document("Texttotrans")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        check_date = document.getBoolean(dates);
                       if (check_date == true){
                           Log.d("Case", "check date: " + check_date);
                            showRecyclerAddToday(dates);
                        }else {
                           showDialog();
                       }
                        Log.d("Case", "DocumentSnapshot data: " + document.getBoolean(dates));
                    } else {
                        Log.d("Case", "No such document");
                    }
                } else {
                    Log.d("Case", "get failed with ", task.getException());
                }
            }
        });


        /**/


    }

    private void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setMessage(getResources().getString(R.string.add_title));
        dialog.setCancelable(true);
        dialog.setPositiveButton(getResources().getString(R.string.add_dialog_button_ok),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog commit = dialog.create();
        commit.show();
    }

    private void showRecyclerAddToday(String date) {
        Log.d("Case", "DocumentSnapshot data: " + "showRecyclerAddToday");
        list = new ArrayList<>();

        firebaseFirestore.collection("WordChallenge")
                .document("Users")
                .collection(preLogin.getString("Email",""))
                .document("Texttotrans")
                .collection(date)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        FirestoreTransModel transModel = new FirestoreTransModel();
                        transModel.setId(document.getLong("id").intValue());
                        transModel.setEng(document.getString("eng"));
                        transModel.setTh(document.getString("th"));
                        transModel.setType(document.getString("type"));
                        transModel.setDaterecord(document.getString("daterecord"));
                        list.add(transModel);
                    }
                } else {
                    Log.d("TGA", "Error getting documents: ", task.getException());
                }
            }
        });

        Log.d("Case", "DocumentSnapshot data: "+ list.size());
        recyclerView.setAdapter(new RecyclerAddwordTodayAdapter(getActivity(),list));

    }


    private void initInstances(View rootView) {
        preAdd = getActivity().getSharedPreferences("ADD",Context.MODE_PRIVATE);
        preLogin = getActivity().getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = rootView.findViewById(R.id.recycler_addwordtoday);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }
}
