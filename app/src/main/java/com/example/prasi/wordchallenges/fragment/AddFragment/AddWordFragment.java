package com.example.prasi.wordchallenges.fragment.AddFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.prasi.wordchallenges.R;
import com.example.prasi.wordchallenges.manager.firestore.FirestoreManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddWordFragment extends Fragment {
    private Spinner spinner;
    private EditText edtAddEnglish,edtAddThai;
    private Button btnAddword;
    private String result;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private FirestoreManager firestoreManager;
    public AddWordFragment() {
        super();
    }

    public static AddWordFragment newInstance() {
        AddWordFragment fragment = new AddWordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_addword, container, false);
        initInstances(rootView);
        //checkCountText();
        return rootView;
    }

    private void initInstances(View rootView) {
        sharedPreferences = getActivity().getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        firestoreManager = new FirestoreManager();
        btnAddword = rootView.findViewById(R.id.btnAddword);
        edtAddThai = rootView.findViewById(R.id.edtAddThai);
        edtAddEnglish = rootView.findViewById(R.id.edtAddEnglish);
        spinner = rootView.findViewById(R.id.spinSpeech);
        String[] speech = getResources().getStringArray(R.array.speech);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,speech);
        spinner.setAdapter(arrayAdapter);

        btnAddword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getActivity(),sharedPreferences.getString("Email",""),Toast.LENGTH_LONG).show();
                addWordToFirestore();
            }
        });
        // Init 'View' instance(s) with rootView.findViewById here
    }

    private void addWordToFirestore() {
        String mail = sharedPreferences.getString("Email","");
        firestoreManager.addTextCollaction(edtAddEnglish.getText().toString().trim(),edtAddThai.getText().toString().trim()
                ,mail,spinner.getSelectedItem().toString(),getActivity());
    }

    private void checkCountText() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dates = simpleDateFormat.format(date);
        SharedPreferences sp = getActivity().getSharedPreferences("ADD", Context.MODE_PRIVATE);

        //Log.d(TAG, sp.getString("Date","")+" "+ dates);
        if (sp.getString("Date", "").trim().equals(dates)) {
            sp.edit().clear().commit();
            //Log.d(TAG,"Not Clear");
        } else {
            sp.edit().clear().commit();
            //Log.d(TAG,"Clear SharedPreference");
        }
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
