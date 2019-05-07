package com.example.newsfeed.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends ViewModel {

    private static final String TAG = LoginViewModel.class.getSimpleName();

    private FirebaseAuth firebaseAuth;
    private MutableLiveData<Boolean> loginSuccessLivaData;


    public LoginViewModel() {

        firebaseAuth = FirebaseAuth.getInstance();
        loginSuccessLivaData = new MutableLiveData<>();

    }


    public MutableLiveData<Boolean> getLoginSuccessLivaData() {
        return loginSuccessLivaData;
    }

    public void setLoginSuccessLivaData(boolean loginSuccess) {
        this.loginSuccessLivaData.setValue(loginSuccess);
    }

    public void loginUser(String email, String password) {

        if (!email.equals("") && !password.equals("")) {

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {

                    Log.d(TAG, "signInWithEmailAndPassword: Login successful");
                    setLoginSuccessLivaData(true);

                } else {

                    Log.e(TAG, "signInWithEmailAndPassword: " + task.getException().getMessage());
                    setLoginSuccessLivaData(false);

                }

            });

        } else {

            Log.e(TAG, "signInWithEmailAndPassword: Fields should not be empty");
            setLoginSuccessLivaData(false);

        }

    }
}
