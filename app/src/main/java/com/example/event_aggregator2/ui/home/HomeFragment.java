package com.example.event_aggregator2.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.event_aggregator2.R;
import com.example.event_aggregator2.databinding.FragmentHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yandex.mapkit.MapKitFactory;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.initialize(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_add_friend:
                        NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.addFriendFragment);
                        break;
                    case R.id.nav_profile:
                        NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.profileFragment);
                        break;
                    case R.id.nav_chat:
                        NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.chatFragment);
                        break;
                }
                return true;
            }
        });
    }
    public void onStop() {
        binding.mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        binding.mapview.onStart();
    }
}