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
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final MutableLiveData<Boolean> RegistrationSuccess = new MutableLiveData<>();

    public void setRegistrationSuccess(boolean RegistrationSuccess) {
        this.RegistrationSuccess.setValue(RegistrationSuccess);
    }

    public MutableLiveData<Boolean> getRegistrationSuccess() {
        return RegistrationSuccess;
    }

    public void registration(String email, String password){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        setRegistrationSuccess(true);
                        this.email = email;
                        this.password = password;
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d("Mytest", "   RegistrationAccess");
                        LoadDataToDataBase(user);
                    } else {
                        setRegistrationSuccess(false);
                        Exception exception = task.getException();
                        if (exception != null) {
                            Log.e("Mytest", "Registration failed", exception);
                        }
                    }
                });
    }
    public void LoadDataToDataBase(FirebaseUser user){
        String userId = user.getUid();
        DatabaseReference userRef = database.getReference("users/" + userId);
        userRef.child("password").setValue(password);
        userRef.child("email").setValue(email);
        Log.d("Mytest", "CountOrganizedEvents set");
    }
}
