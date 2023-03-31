package com.example.event_aggregator2.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isLogged = new MutableLiveData<>(null);

    public MutableLiveData<Boolean> isLogged() {
        return isLogged;
    }


}
