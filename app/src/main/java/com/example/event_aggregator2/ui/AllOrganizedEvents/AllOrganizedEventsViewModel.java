package com.example.event_aggregator2.ui.AllOrganizedEvents;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.event_aggregator2.ui.organizedEvents.OrganizedEvent;
import com.example.event_aggregator2.ui.organizedEvents.OrganizedEventsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class AllOrganizedEventsViewModel extends ViewModel {
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private MutableLiveData<OrganizedEvent> organizedEvent = new MutableLiveData<>();

    public MutableLiveData<OrganizedEvent> getOrganizedEvent() {
        return organizedEvent;
    }

    public void setOrganizedEvent(OrganizedEvent organizedEvent) {
        this.organizedEvent.setValue(organizedEvent);
    }

    public void GetDataFromDataBase(){
        DatabaseReference userRef = database.getReference("users/");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    for(DataSnapshot CreatedEvent : userSnapshot.child("CreatedEvents").getChildren()){
                        String title = CreatedEvent.child("topic").getValue(String.class);
                        String idEvent = CreatedEvent.getKey();
                        String uri = CreatedEvent.child("imageEvent").getValue(String.class);
                        OrganizedEvent organizedEvent = new OrganizedEvent(title, uri, idEvent);
                        Log.d("Mytest", idEvent);
                        setOrganizedEvent(organizedEvent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обработка ошибок
            }
        });
    }
}
