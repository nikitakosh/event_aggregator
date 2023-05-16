package com.example.event_aggregator2.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.event_aggregator2.R;
import com.example.event_aggregator2.databinding.FragmentLoginBinding;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    private LoginViewModel viewModel;
    private FragmentLoginBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String email = binding.email.getText().toString();
                    String password = binding.password.getText().toString();
                    viewModel.login(email, password);
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
                }

            }
        });
        viewModel.getSignInSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (Boolean.TRUE.equals(viewModel.getSignInSuccess().getValue())){
                    NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.profileFragment);
                }
                else{
                    Toast.makeText(getContext(), "Ошибка авторизации", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.goToRegistratration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.registrationFragment);
            }
        });
    }

}