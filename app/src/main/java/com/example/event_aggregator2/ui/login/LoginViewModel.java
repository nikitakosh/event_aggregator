package com.example.event_aggregator2.ui.login;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {
    private FirebaseAuth mAuth;
    private boolean signInSuccess = false;

    public boolean login(String email, String password){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Вход выполнен успешно, получаем информацию о пользователе
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("Mytest", "access");
                            signInSuccess = true;
                        } else {
                            // Вход не выполнен, выводим сообщение об ошибке
                            Log.d("Mytest", "error");
                        }
                    }
                });
        return signInSuccess;
    }




}
