package com.example.event_aggregator2.ui.profile;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.event_aggregator2.R;
import com.example.event_aggregator2.databinding.FragmentMainBinding;
import com.example.event_aggregator2.databinding.FragmentProfileBinding;
import com.example.event_aggregator2.databinding.FragmentRegistrationBinding;
import com.example.event_aggregator2.ui.create_profile.CreateProfileViewModel;
import com.example.event_aggregator2.ui.home.HomeFragment;
import com.example.event_aggregator2.ui.main.MainViewModel;
import com.example.event_aggregator2.ui.registration.RegistrationViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class ProfileFragment extends Fragment {
    private ProfileViewModel viewModel;
    private FragmentProfileBinding binding;


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
        viewModel.LoadOrganizedEventFromDataBase();
        viewModel.LoadVisitedEventFromDataBase();
        viewModel.GetDataFromDataBase();
        viewModel.GetProfileImageFromDataBase();
        binding.SeeAllVisitedEvents.setVisibility(View.INVISIBLE);
        binding.GoToOrganizedEventsFragment.setVisibility(View.INVISIBLE);
        viewModel.getIdVisitedEvent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.SeeAllVisitedEvents.setVisibility(View.VISIBLE);
            }
        });
        viewModel.getIdOrganizedEvent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.GoToOrganizedEventsFragment.setVisibility(View.VISIBLE);
            }
        });


        viewModel.getProfilePhotoUri().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String uri) {
                Glide.with(requireActivity()).load(uri).into(binding.PhotoProfile);
                binding.PhotoProfile.setImageResource(R.drawable.ava);

            }
        });
        binding.VisitedEventPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.getIdVisitedEvent().getValue() != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("idEvent", viewModel.getIdVisitedEvent().getValue());
                    NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.eventFragment, bundle);
                }
            }
        });
        binding.photoCreatedEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.getIdOrganizedEvent().getValue() != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("idEvent", viewModel.getIdOrganizedEvent().getValue());
                    NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.eventFragment, bundle);
                }
            }
        });
        viewModel.getIsOrganizer().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    binding.IsOrganizer.setText("организатор");
                }
                else{
                    binding.IsOrganizer.setText("посетитель");
                    binding.GoToCreateEvent.setVisibility(View.GONE);
                    binding.textView16.setVisibility(View.GONE);
                    binding.photoCreatedEvent.setVisibility(View.GONE);
                    binding.GoToOrganizedEventsFragment.setVisibility(View.GONE);
                    binding.titleCreatedEvent.setVisibility(View.GONE);
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
        binding.ToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.homeFragment);
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
                if (viewModel.getIdOrganizedEvent() != null){
                    NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.organizedEventsFragment);
                }
            }
        });
        viewModel.getPhotoCreatedEvent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(requireActivity())
                        .asBitmap()
                        .load(s)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                Bitmap scaledImage = Bitmap.createScaledBitmap(resource, 70, 70, true);
                                binding.photoCreatedEvent.setImageBitmap(scaledImage);

                            }
                        });
            }
        });
        viewModel.getTitleCreatedEvent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.titleCreatedEvent.setText(s);
            }
        });
        viewModel.getPhotoVisitedEvent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(requireActivity())
                        .asBitmap()
                        .load(s)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                Bitmap scaledImage = Bitmap.createScaledBitmap(resource, 70, 70, true);
                                binding.VisitedEventPhoto.setImageBitmap(scaledImage);

                            }
                        });
            }
        });
        viewModel.getTitleVisitedEvent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.VisitedEventTitle.setText(s);
            }
        });
        binding.GoToAllCreatedEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.allOrganizedEventFragment);
            }
        });
        binding.SeeAllVisitedEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.getIdVisitedEvent() != null){
                    NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.visitedEventsFragment);
                }
            }
        });

    }

}