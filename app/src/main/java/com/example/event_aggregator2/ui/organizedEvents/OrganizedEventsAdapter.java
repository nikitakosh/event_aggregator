package com.example.event_aggregator2.ui.organizedEvents;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.event_aggregator2.R;
import com.example.event_aggregator2.databinding.OrganizedEventItemBinding;

import java.util.ArrayList;

public class OrganizedEventsAdapter extends RecyclerView.Adapter<OrganizedEventsAdapter.Adapter> {
    private ArrayList<OrganizedEvent> OrganizedEvents;
    private LayoutInflater inflater;
    private Context context;
    private NavController navController;


    public OrganizedEventsAdapter(ArrayList<OrganizedEvent> organizedEvents, Context context, NavController navController) {
        this.OrganizedEvents = organizedEvents;
        this.context = context;
        this.navController = navController;
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
        OrganizedEvent event = OrganizedEvents.get(position);
        holder.binding.title.setText(event.getTitle());
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(event.getEventUri())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Bitmap scaledImage = Bitmap.createScaledBitmap(resource, 70, 70, true);
                        holder.binding.eventImage.setImageBitmap(scaledImage);
                    }
                });
        Bundle bundle = new Bundle();
        bundle.putString("idEvent", event.getIdEvent());

        holder.binding.eventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.eventFragment, bundle);
            }
        });
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
//        public void bind(OrganizedEvent organizedEvent, @NonNull OrganizedEventsAdapter.Adapter holder){
////            binding.title.setText(organizedEvent.getTitle());
//
//        }
        public OrganizedEventItemBinding getOrganizedEventItemBinding(){
            return this.binding;
        }
    }
    public void clear(){
        OrganizedEvents.clear();
    }
}
