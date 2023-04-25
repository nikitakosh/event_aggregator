package com.example.event_aggregator2.ui.main;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainViewModel extends ViewModel {
    private FirebaseAuth mAuth;
    boolean auth (){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            return true;
        }
        return false;
    }

}
