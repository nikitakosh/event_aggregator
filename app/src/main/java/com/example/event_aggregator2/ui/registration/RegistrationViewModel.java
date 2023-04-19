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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

public class RegistrationViewModel extends ViewModel {
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private boolean RegistrationSuccess = false;


    public boolean registration(String email, String password){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        RegistrationSuccess = true;
                        this.email = email;
                        this.password = password;
                        FirebaseUser user = mAuth.getCurrentUser();
                        LoadDataToDataBase(user);
                    } else {
                        Exception exception = task.getException();
                        if (exception != null) {
                            Log.e("Mytest", "Registration failed", exception);
                        }
                    }
                });
        return RegistrationSuccess;
    }
    public void LoadDataToDataBase(FirebaseUser user){
        String userId = user.getUid();
        DatabaseReference userRef = database.getReference("users/" + userId);
        userRef.child("password").setValue(password);
        userRef.child("email").setValue(email);
    }
}
