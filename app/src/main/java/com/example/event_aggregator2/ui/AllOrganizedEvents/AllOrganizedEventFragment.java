package com.example.event_aggregator2.ui.AllOrganizedEvents;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.event_aggregator2.R;
import com.example.event_aggregator2.databinding.FragmentAllOrganizedEventBinding;
import com.example.event_aggregator2.ui.organizedEvents.OrganizedEvent;
import com.example.event_aggregator2.ui.organizedEvents.OrganizedEventsAdapter;
import com.example.event_aggregator2.ui.profile.ProfileFragment;
import com.example.event_aggregator2.ui.visitedEvents.VisitedEventsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class AllOrganizedEventFragment extends Fragment {
    private String title;
    private String uri;
    FragmentAllOrganizedEventBinding binding;
    AllOrganizedEventsViewModel viewModel;
    private ArrayList<OrganizedEvent> organizedEvents = new ArrayList<>();
    OrganizedEventsAdapter organizedEventsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AllOrganizedEventsViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllOrganizedEventBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.AllOrganizedEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel.GetDataFromDataBase();
        NavController navController = NavHostFragment.findNavController(AllOrganizedEventFragment.this);
        viewModel.getOrganizedEvent().observe(getViewLifecycleOwner(), new Observer<OrganizedEvent>() {
            @Override
            public void onChanged(OrganizedEvent organizedEvent) {
                if (organizedEvent != null){
                    organizedEvents.add(organizedEvent);
                    organizedEventsAdapter = new OrganizedEventsAdapter(organizedEvents, requireContext(), navController);
                    binding.AllOrganizedEvents.setAdapter(organizedEventsAdapter);
                }

            }
        });

    }

    @Override
    public void onDestroyView() {
        viewModel.setOrganizedEvent(null);
        if(organizedEventsAdapter != null){
            organizedEventsAdapter.clear();
        }
        super.onDestroyView();
    }


}