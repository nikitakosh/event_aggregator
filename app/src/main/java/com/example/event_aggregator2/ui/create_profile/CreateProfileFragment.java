package com.example.event_aggregator2.ui.create_profile;

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
import android.widget.EditText;

import com.example.event_aggregator2.R;
import com.example.event_aggregator2.databinding.FragmentCreateProfileBinding;


public class CreateProfileFragment extends Fragment {
    private CreateProfileViewModel viewModel;
    private FragmentCreateProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CreateProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateProfileBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.GetDataFromDataBase();
        viewModel.getEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.email.setText(s);
            }
        });
        viewModel.getPassword().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.password.setText(s);
            }
        });
        binding.CreateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.name.getText().toString();
                String surname = binding.surname.getText().toString();
                String city = binding.city.getText().toString();
                String password = binding.password.getText().toString();
                String AboutYourself = binding.AboutYorself.getText().toString();
                String email = binding.email.getText().toString();
                viewModel.LoadDataToDataBase(name, surname, city, password, AboutYourself, email);
                NavHostFragment.findNavController(CreateProfileFragment.this).navigate(R.id.profileFragment);
            }
        });
    }
}