package com.example.event_aggregator2.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.event_aggregator2.R;
import com.example.event_aggregator2.databinding.FragmentMainBinding;
import com.example.event_aggregator2.ui.login.LoginFragment;


public class MainFragment extends Fragment {
    private MainViewModel viewModel;
    private FragmentMainBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (viewModel.auth()){
            NavHostFragment.findNavController(MainFragment.this).navigate(R.id.profileFragment);
        }
        else{
            NavHostFragment.findNavController(MainFragment.this).navigate(R.id.loginFragment);
        }
    }
}