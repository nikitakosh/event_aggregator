package com.example.event_aggregator2.ui.visitedEvents;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.event_aggregator2.R;
import com.example.event_aggregator2.databinding.OrganizedEventItemBinding;
import com.example.event_aggregator2.databinding.VisitedEventItemBinding;
import com.example.event_aggregator2.ui.organizedEvents.OrganizedEvent;
import com.example.event_aggregator2.ui.organizedEvents.OrganizedEventsAdapter;

import java.util.ArrayList;

public class VisitedEventsAdapter extends RecyclerView.Adapter<VisitedEventsAdapter.Adapter>{
    private ArrayList<VisitedEvent> VisitedEvents;
    private LayoutInflater inflater;
    private Context context;
    NavController navController;


    public VisitedEventsAdapter(ArrayList<VisitedEvent> visitedEvents, Context context, NavController navController) {
        this.VisitedEvents = visitedEvents;
        this.context = context;
        this.navController = navController;
    }

    @NonNull
    @Override
    public VisitedEventsAdapter.Adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater==null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        VisitedEventItemBinding binding = VisitedEventItemBinding.inflate(inflater, parent, false);
        return new Adapter(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitedEventsAdapter.Adapter holder, int position) {
        VisitedEvent event = VisitedEvents.get(position);
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
        bundle.putString("idEvent", event.getEventId());

        holder.binding.eventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.eventFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return VisitedEvents.size();
    }

    public class Adapter extends RecyclerView.ViewHolder {
        private VisitedEventItemBinding binding;
        public Adapter(VisitedEventItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
//        public void bind(VisitedEvent visitedEvent, @NonNull VisitedEventsAdapter.Adapter holder){
//            binding.title.setText(visitedEvent.getTitle());
//            Glide.with(holder.itemView.getContext()).load(visitedEvent.getEventUri()).into(binding.eventImage);
//        }
        public VisitedEventItemBinding getVisitedEventItemBinding(){
            return this.binding;
        }
    }
    public void clear(){
        VisitedEvents.clear();
    }
}
