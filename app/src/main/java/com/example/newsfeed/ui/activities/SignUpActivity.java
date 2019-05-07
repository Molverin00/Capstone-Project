package com.example.newsfeed.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.newsfeed.R;
import com.example.newsfeed.viewModel.SignUpViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();


    @BindView(R.id.signUpEmailInputLayout) TextInputLayout signUpEmailInputLayout;
    @BindView(R.id.signUpPasswordInputLayout) TextInputLayout signUpPasswordInputLayout;
    @BindView(R.id.confirmPasswordInputLayout) TextInputLayout confirmPasswordInputLayout;

    @BindView(R.id.signUpEmailInputEditText) TextInputEditText signUpEmailInputEditText;
    @BindView(R.id.signUpPasswordInputEditText) TextInputEditText signUpPasswordInputEditText;
    @BindView(R.id.confirmPasswordInputEditText) TextInputEditText confirmPasswordInputEditText;

    @BindView(R.id.btn_create_account) Button createAccountButton;


    private SignUpViewModel signUpViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);


        createAccountButton.setOnClickListener(v -> {

            String email = signUpEmailInputEditText.getEditableText().toString();
            String password = signUpPasswordInputEditText.getEditableText().toString();

            signUpViewModel.createUser(email, password);

        });
    }
}
