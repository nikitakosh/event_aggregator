package com.example.event_aggregator2.ui.registration;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class RegistrationViewModel extends ViewModel {
    private FirebaseAuth mAuth;
    private boolean RegistrationSuccess = false;


    public boolean registration(String email, String password){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d("Mytest", "access");
                        RegistrationSuccess = true;
                    } else {
                        Exception exception = task.getException();
                        if (exception != null) {
                            Log.e("Mytest", "Registration failed", exception);
                        }
                    }
                });
        return RegistrationSuccess;
    }
}
