package com.example.newsfeed.viewModel;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpViewModel extends ViewModel {

    private static final String TAG = SignUpViewModel.class.getSimpleName();


    private FirebaseAuth firebaseAuth;


    public SignUpViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void createUser(String email, String password) {

        if (!email.equals("") && !password.equals("")) {

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    Log.d(TAG, "createUserWithEmailAndPassword: New user added");

                } else {

                    Log.e(TAG, "createUserWithEmailAndPassword: " + task.getException().getMessage());

                }
            });

        }
    }
}
