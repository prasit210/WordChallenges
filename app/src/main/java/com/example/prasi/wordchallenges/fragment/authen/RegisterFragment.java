package com.example.prasi.wordchallenges.fragment.authen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.prasi.wordchallenges.R;
import com.example.prasi.wordchallenges.activity.LoginActivity;
import com.example.prasi.wordchallenges.manager.firestore.FirestoreManager;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RegisterFragment extends Fragment {
    private EditText edtEmail,edtPswords1,edtPswords2,edtName,edtLastname,edtTel,edtCoutry;
    private Spinner spinner;
    private Button btnAccept,btnCancel;
    private FirestoreManager firestoreManager;
    private Timestamp timestamp;
    private String email,pass1,pass2,name,lastname,tel,coutry,time,result;
    public RegisterFragment() {
        super();
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        long timeNow = System.currentTimeMillis();
        firestoreManager = new FirestoreManager();
        spinner = (Spinner)rootView.findViewById(R.id.spCoutry);
        btnAccept = (Button)rootView.findViewById(R.id.btnAccepRegister);
        btnCancel =(Button)rootView.findViewById(R.id.btnCancelRegister);
        edtEmail = (EditText)rootView.findViewById(R.id.edtRegister1);
        edtPswords1 = (EditText)rootView.findViewById(R.id.edtRegister2);
        edtPswords2 = (EditText)rootView.findViewById(R.id.edtRegister2and3);
        edtName = (EditText)rootView.findViewById(R.id.edtRegister4);
        edtLastname = (EditText)rootView.findViewById(R.id.edtRegister5);
        edtTel = (EditText)rootView.findViewById(R.id.edtRegister6);
        // Init 'View' instance(s) with rootView.findViewById here

        String[] ct = getResources().getStringArray(R.array.coutry);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,ct);
        spinner.setAdapter(arrayAdapter);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertToFirestore();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,new LoginFragment()).commit();
            }
        });
    }

    private void insertToFirestore() {

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
        time = format.format(date);


        email = edtEmail.getText().toString().trim();
        pass1 = edtPswords1.getText().toString().trim();
        pass2 = edtPswords2.getText().toString().trim();
        name = edtName.getText().toString().trim();
        lastname = edtLastname.getText().toString().trim();
        tel = edtTel.getText().toString().trim();
        coutry = spinner.getSelectedItem().toString();


        if (email.matches("") || pass1.matches("") || pass2.matches("") || name.matches("") || lastname.matches("") || tel.matches("")){
            Toast.makeText(getActivity(),"Please Input Text", Toast.LENGTH_SHORT).show();
        }else {
            if (pass1.equals(pass2)){
                result = firestoreManager.RegisterInFirestore(email,pass1,name,lastname,tel,coutry,time);
                if (result == null){
                    Toast.makeText(getActivity(),"SUCCESS",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }else {
                    Toast.makeText(getActivity(),"FAIL",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getActivity(),"Fail",Toast.LENGTH_SHORT).show();
            }
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
