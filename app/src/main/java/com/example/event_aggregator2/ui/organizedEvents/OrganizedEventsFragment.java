package com.example.event_aggregator2.ui.organizedEvents;

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
import android.view.View;
import android.view.ViewGroup;

import com.example.event_aggregator2.databinding.FragmentOrganizedEventsBinding;
import com.example.event_aggregator2.ui.create_profile.CreateProfileViewModel;

import java.util.ArrayList;

public class OrganizedEventsFragment extends Fragment {
    private OrganizedEventsViewModel viewModel;
    private ArrayList<OrganizedEvent> organizedEvents = new ArrayList<>();
    private FragmentOrganizedEventsBinding binding;
    private OrganizedEventsAdapter organizedEventsAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(OrganizedEventsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrganizedEventsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.OrganizedEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel.GetDataFromDataBase();
        NavController navController = NavHostFragment.findNavController(OrganizedEventsFragment.this);
        viewModel.getOrganizedEvent().observe(getViewLifecycleOwner(), new Observer<OrganizedEvent>() {
            @Override
            public void onChanged(OrganizedEvent organizedEvent) {
                if (organizedEvent != null){
                    Log.d("Mytest", "fragment" + organizedEvent.getTitle());
                    organizedEvents.add(organizedEvent);
                    organizedEventsAdapter = new OrganizedEventsAdapter(organizedEvents, requireContext(), navController);
                    binding.OrganizedEvents.setAdapter(organizedEventsAdapter);
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.setOrganizedEvent(null);
        if(organizedEventsAdapter != null){
            organizedEventsAdapter.clear();
        }
    }
}