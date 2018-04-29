package com.example.prasi.wordchallenges.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.prasi.wordchallenges.R;
import com.example.prasi.wordchallenges.activity.MainActivity;
import com.example.prasi.wordchallenges.manager.firestore.FirestoreManager;
import com.example.prasi.wordchallenges.manager.firestore.FirestorePersonalModel;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;


public class LoginFragment extends Fragment {
    private EditText edtUser,edtPswords;
    private FirestorePersonalModel firestorePersonalModel;
    private FirestoreManager firestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private Button btnLogin,btnRegister;
    private ImageView imageView;
    private String result,email,password;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public LoginFragment() {
        super();
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        sharedPreferences = getActivity().getSharedPreferences("LOGIN",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        firestorePersonalModel = new FirestorePersonalModel();
        firestoreManager = new FirestoreManager();
        firebaseFirestore = FirebaseFirestore.getInstance();
        edtUser = (EditText) rootView.findViewById(R.id.edtId);
        edtPswords = (EditText)rootView.findViewById(R.id.edtLoginPassword);
        btnLogin = (Button)rootView.findViewById(R.id.btnAccept);
        btnRegister = (Button)rootView.findViewById(R.id.btnToRegister);
        imageView = (ImageView)rootView.findViewById(R.id.imageView3);
        loginButton = (LoginButton)rootView.findViewById(R.id.login_button);


        loginButton.setReadPermissions("email","public_profile");
        loginButton.setFragment(this);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Toast.makeText(getActivity(),"Click",Toast.LENGTH_LONG).show();
                String userid = loginResult.getAccessToken().getUserId();
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        displayUserInfo(object);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields","first_name,last_name,email,id");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        // Init 'View' instance(s) with rootView.findViewById here
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"GGE",Toast.LENGTH_LONG).show();
                loginWithFirestore();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"GGE",Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container,new RegisterFragment())
                        .setCustomAnimations(R.anim.lefttoright,R.anim.righttoleft)
                        .commit();

            }
        });
    }

    private void loginWithFirestore() {
        email = edtUser.getText().toString().trim();
        password = edtPswords.getText().toString().trim();

        if (email.matches("") || password.matches("")){
            Toast.makeText(getActivity(),"FAIL",Toast.LENGTH_SHORT).show();
        }else {
            firebaseFirestore.collection("WordChallenge").document("Users").collection(email).document("PersonDetail").get().addOnCompleteListener
                    (new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot != null && documentSnapshot.exists()) {
                                    Log.d("GGWP", "DocumentSnapshot data: " + documentSnapshot.getData());
                                    if (email.equals(documentSnapshot.getString("email")) && password.equals(documentSnapshot.getString("password"))) {
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        editor.putBoolean("Status", true);
                                        editor.putString("Type","Firestore");
                                        editor.putString("Email", documentSnapshot.getString("email"));
                                        editor.putString("Name", documentSnapshot.getString("name"));
                                        editor.putString("Surname", documentSnapshot.getString("lastname"));
                                        editor.putString("Coutry", documentSnapshot.getString("email"));
                                        editor.putString("Tel", documentSnapshot.getString("tel"));
                                        editor.putString("DateRegister", documentSnapshot.getString("dateregister"));
                                        editor.commit();
                                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                        getActivity().finish();
                                        //getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Fail Login", Toast.LENGTH_SHORT).show();
                                    Log.d("GGWP", "No such document");
                                }
                            } else {
                                Log.d("GGWP", "get failed with ", task.getException());
                            }
                        }
                    });
        }
    }

    public void displayUserInfo(JSONObject object) {
        String fist_name = "",last_name = "",email = "",id = " ";

        try {
            fist_name = object.getString("first_name");
            last_name = object.getString("last_name");
            email = object.getString("email");
            id = object.getString("id");
        }catch (JSONException e){
            e.printStackTrace();
        }

        //Toast.makeText(getActivity(),fist_name,Toast.LENGTH_LONG).show();
        if (fist_name != null){
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
            String time = format.format(date);

            sharedPreferences = getActivity().getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            Intent intent = new Intent(getActivity(), MainActivity.class);
            editor.putBoolean("Status",true);
            editor.putString("Type","Facebook");
            editor.putString("Name",fist_name);
            editor.putString("Surname",last_name);
            editor.putString("Email",email);
            editor.putString("ID",id);
            editor.commit();

            firestoreManager.RegisterInFirestoreWithFacebook(email,fist_name,last_name,id,time);
            startActivity(intent);
            getActivity().finish();

        }
        //Glide.with(this).load("https://graph.facebook.com/" + id + "/picture?type=large").into(imageView);



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
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
