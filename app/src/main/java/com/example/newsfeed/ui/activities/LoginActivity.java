package com.example.newsfeed.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


import com.example.newsfeed.viewModel.LoginViewModel;
import com.example.newsfeed.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();


    @BindView(R.id.emailInputLayout) TextInputLayout emailInputLayout;
    @BindView(R.id.passwordInputLayout) TextInputLayout passwordInputLayout;

    @BindView(R.id.emailInputEditText) EditText emailInputEditText;
    @BindView(R.id.passwordInputEditText) EditText passwordInputEditText;

    @BindView(R.id.btn_login) Button loginButton;
    @BindView(R.id.btn_sign_up) Button signUpButton;

    @BindView(R.id.adView) AdView mAdView;


    private LoginViewModel loginViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        loginViewModel.getLoginSuccessLivaData().observe(this, aBoolean -> {

            if (aBoolean != null && aBoolean) {

                navigateToMain();

            }

        });


        loginButton.setOnClickListener(v -> {
            String email = emailInputEditText.getEditableText().toString();
            String password = passwordInputEditText.getEditableText().toString();

            loginViewModel.loginUser(email, password);

        });


        signUpButton.setOnClickListener(v -> {
            navigateToSignUp();
        });

    }


    public void navigateToSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }


    public void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
