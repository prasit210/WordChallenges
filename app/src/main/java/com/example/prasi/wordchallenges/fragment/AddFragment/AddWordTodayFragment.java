package com.example.prasi.wordchallenges.fragment.AddFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.prasi.wordchallenges.R;
import com.example.prasi.wordchallenges.manager.firestore.FirestoreTransModel;
import com.example.prasi.wordchallenges.view.RecyclerAddwordTodayAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class AddWordTodayFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button button;
    private String doc;
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
        show();




        return rootView;
    }

    private void show() {
        list = new ArrayList<>();
        firebaseFirestore.collection("WordChallenge").document("Users").collection(preLogin.getString("Email",""))
                .document("Texttotrans").collection(preAdd.getString("Date","")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        FirestoreTransModel transModel = new FirestoreTransModel();
                        transModel.setId("12");
                        transModel.setEng(document.getString("eng"));
                        transModel.setTh(document.getString("th"));
                        transModel.setType(document.getString("type"));
                        transModel.setDaterecord(document.getString("daterecord"));
                        list.add(transModel);

                        //Log.d("TGA", document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d("TGA", "Error getting documents: ", task.getException());
                }

            }
        });

        recyclerView.setAdapter(new RecyclerAddwordTodayAdapter(getActivity(),list));


        //recyclerView.setAdapter(new RecyclerAddwordTodayAdapter(getActivity(),readDataFirestore()));
    }





    private void initInstances(View rootView) {

        preAdd = getActivity().getSharedPreferences("ADD",Context.MODE_PRIVATE);
        preLogin = getActivity().getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        firebaseFirestore = FirebaseFirestore.getInstance();
        //button = rootView.findViewById(R.id.testbtn);
        recyclerView = rootView.findViewById(R.id.recycler_addwordtoday);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        // Init 'View' instance(s) with rootView.findViewById here
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
