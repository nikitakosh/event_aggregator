package com.example.event_aggregator2.ui.organizedEvents;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.event_aggregator2.databinding.FragmentOrganizedEventsBinding;
import com.example.event_aggregator2.ui.create_profile.CreateProfileViewModel;

import java.util.ArrayList;

public class OrganizedEventsFragment extends Fragment {
    private OrganizedEventsViewModel viewModel;
    ArrayList<OrganizedEvent> organizedEvents = new ArrayList<>();
    private FragmentOrganizedEventsBinding binding;
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
//        OrganizedEvent event1 = new OrganizedEvent("Тестовое мероприятие 1");
//        OrganizedEvent event2 = new OrganizedEvent("Тестовое мероприятие 2");
//        organizedEvents.add(event1);
//        organizedEvents.add(event2);
//        OrganizedEventsAdapter organizedEventsAdapter = new OrganizedEventsAdapter(organizedEvents, requireContext());
//        binding.OrganizedEvents.setAdapter(organizedEventsAdapter);
    }
}