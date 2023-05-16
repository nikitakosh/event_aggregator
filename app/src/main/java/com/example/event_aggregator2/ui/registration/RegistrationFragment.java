package com.example.event_aggregator2.ui.registration;

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
import com.example.event_aggregator2.databinding.FragmentRegistrationBinding;


public class RegistrationFragment extends Fragment {
    private RegistrationViewModel viewModel;
    private FragmentRegistrationBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String email = binding.email.getText().toString();
                    String password = binding.password.getText().toString();
                    viewModel.registration(email, password);
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getRegistrationSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (Boolean.TRUE.equals(viewModel.getRegistrationSuccess().getValue())){
                    NavHostFragment.findNavController(RegistrationFragment.this).navigate(R.id.createProfileFragment);
                }
                else{
                    Toast.makeText(getContext(), "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}