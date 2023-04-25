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
        viewModel.getName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.name.setText(s);
            }
        });
        viewModel.getSurname().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.surname.setText(s);
            }
        });
        viewModel.getCity().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.city.setText(s);
            }
        });
        viewModel.getAboutYourself().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.AboutYorself.setText(s);
            }
        });
        viewModel.getEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.email.setText(s);
            }
        });
        viewModel.getIsOrganizer().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.IsOrganizer.setChecked(aBoolean);
            }
        });
        binding.CreateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.name.getText().toString();
                String surname = binding.surname.getText().toString();
                String city = binding.city.getText().toString();
                String AboutYourself = binding.AboutYorself.getText().toString();
                String email = binding.email.getText().toString();
                boolean IsOrganizer;
                if (binding.IsOrganizer.isChecked()){
                    IsOrganizer = true;
                }
                else{
                    IsOrganizer = false;
                }
                viewModel.LoadDataToDataBase(name, surname, city, AboutYourself, email, IsOrganizer);
                NavHostFragment.findNavController(CreateProfileFragment.this).navigate(R.id.profileFragment);
            }
        });
    }
}