package com.example.event_aggregator2.ui.organizedEvents;

import android.content.Context;
import android.view.LayoutInflater;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.event_aggregator2.databinding.OrganizedEventItemBinding;
import java.util.ArrayList;

public class OrganizedEventsAdapter extends RecyclerView.Adapter<OrganizedEventsAdapter.Adapter> {
    private ArrayList<OrganizedEvent> OrganizedEvents;
    private LayoutInflater inflater;
    private Context context;

    public OrganizedEventsAdapter(ArrayList<OrganizedEvent> organizedEvents, Context context) {
        this.OrganizedEvents = organizedEvents;
        this.context = context;
    }

    @NonNull
    @Override
    public OrganizedEventsAdapter.Adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater==null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        OrganizedEventItemBinding binding = OrganizedEventItemBinding.inflate(inflater, parent, false);
        return new Adapter(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizedEventsAdapter.Adapter holder, int position) {
        holder.bind(OrganizedEvents.get(position), holder);
    }

    @Override
    public int getItemCount() {
        return OrganizedEvents.size();
    }

    public class Adapter extends RecyclerView.ViewHolder {
        private OrganizedEventItemBinding binding;
        public Adapter(OrganizedEventItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(OrganizedEvent organizedEvent, @NonNull OrganizedEventsAdapter.Adapter holder){
            binding.title.setText(organizedEvent.getTitle());
            Glide.with(holder.itemView.getContext()).load(organizedEvent.getEventUri()).into(binding.eventImage);
        }
        public OrganizedEventItemBinding getOrganizedEventItemBinding(){
            return this.binding;
        }
    }
}
