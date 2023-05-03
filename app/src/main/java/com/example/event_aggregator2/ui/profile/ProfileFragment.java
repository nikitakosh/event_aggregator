package com.example.event_aggregator2.ui.profile;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.event_aggregator2.R;
import com.example.event_aggregator2.databinding.FragmentMainBinding;
import com.example.event_aggregator2.databinding.FragmentProfileBinding;
import com.example.event_aggregator2.databinding.FragmentRegistrationBinding;
import com.example.event_aggregator2.ui.create_profile.CreateProfileViewModel;
import com.example.event_aggregator2.ui.main.MainViewModel;
import com.example.event_aggregator2.ui.registration.RegistrationViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class ProfileFragment extends Fragment {
    private ProfileViewModel viewModel;
    private FragmentProfileBinding binding;
    private CreateProfileViewModel createProfileViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.GetDataFromDataBase();
        GetProfileImageFromDataBase();
        viewModel.getIsOrganizer().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    binding.IsOrganizer.setText("организатор");
                }
                else{
                    binding.IsOrganizer.setText("посетитель");
                    binding.GoToCreateEvent.setVisibility(View.GONE);
                }
            }
        });
        viewModel.getNameSurname().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.NameSurname.setText(s);
            }
        });
        viewModel.getAboutYourself().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("")){
                    binding.AboutYourself.setVisibility(View.GONE);
                    binding.frame.setMaxHeight(20);
                    binding.frame.setVisibility(View.INVISIBLE);
                }
                else{
                    binding.AboutYourself.setText(s);
                }
            }
        });
        viewModel.getCity().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.city.setText(s);
            }
        });
        binding.GoToChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.createProfileFragment);
            }
        });
        binding.SeeAllVisitedEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.allVisitedEventsFragment);
            }
        });
        binding.ToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.homeFragment);
            }
        });
        binding.event1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.eventFragment);
            }
        });
        binding.GoToCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.createEventFragment);
            }
        });
        binding.GoToOrganizedEventsFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.organizedEventsFragment);
            }
        });

    }
    public void GetProfileImageFromDataBase(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageRef = storage.getReference().child(userId);
        storageRef.child("profileImage.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (!TextUtils.isEmpty(uri.toString())) {
                    Glide.with(requireActivity()).load(uri).into(binding.PhotoProfile);
                }
            }
        });
    }
}