package com.example.prasi.wordchallenges.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.prasi.wordchallenges.R;
import com.example.prasi.wordchallenges.activity.MainActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class LoginFragment extends Fragment {
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private ImageView imageView;
    private SharedPreferences sharedPreferences;
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
            sharedPreferences = this.getActivity().getSharedPreferences("LOGIN_FB", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            editor.putBoolean("Status",true);
            editor.putString("Name",fist_name);
            editor.putString("Surname",last_name);
            editor.putString("Email",email);
            editor.putString("ID",id);
            editor.commit();
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
