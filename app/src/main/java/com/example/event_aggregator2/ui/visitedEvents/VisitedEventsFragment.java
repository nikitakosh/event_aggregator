package com.example.event_aggregator2.ui.visitedEvents;

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

import com.example.event_aggregator2.R;
import com.example.event_aggregator2.databinding.FragmentVisitedEventsBinding;
import com.example.event_aggregator2.ui.organizedEvents.OrganizedEvent;
import com.example.event_aggregator2.ui.organizedEvents.OrganizedEventsAdapter;
import com.example.event_aggregator2.ui.organizedEvents.OrganizedEventsFragment;
import com.example.event_aggregator2.ui.organizedEvents.OrganizedEventsViewModel;

import java.util.ArrayList;

public class VisitedEventsFragment extends Fragment {
    private ArrayList<VisitedEvent> VisitedEvents = new ArrayList<>();
    private VisitedEventsViewModel viewModel;
    private FragmentVisitedEventsBinding binding;
    private VisitedEventsAdapter visitedEventsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(VisitedEventsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVisitedEventsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.VisitedEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel.GetDataFromDataBase();
        viewModel.getIdEvent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null){
                    Log.d("Mytest", "fragment_getIdEvent " + s);
                    viewModel.GetVisitedEvent(s);
                }

            }
        });
        NavController navController = NavHostFragment.findNavController(VisitedEventsFragment.this);
        viewModel.getVisitedEvent().observe(getViewLifecycleOwner(), new Observer<VisitedEvent>() {
            @Override
            public void onChanged(VisitedEvent visitedEvent) {
                if (visitedEvent != null){
                    Log.d("Mytest", "fragment" + visitedEvent.getTitle());
                    VisitedEvents.add(visitedEvent);
                    visitedEventsAdapter = new VisitedEventsAdapter(VisitedEvents, requireContext(), navController);
                    binding.VisitedEvents.setAdapter(visitedEventsAdapter);
                }
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.setVisitedEvent(null);
        viewModel.setIdEvent(null);
        if(visitedEventsAdapter != null){
            visitedEventsAdapter.clear();
        }
    }
}