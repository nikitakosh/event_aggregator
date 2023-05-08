package com.example.event_aggregator2.ui.visitedEvents;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.event_aggregator2.ui.organizedEvents.OrganizedEvent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class VisitedEventsViewModel extends ViewModel {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private MutableLiveData<VisitedEvent> visitedEvent = new MutableLiveData<>();
    private MutableLiveData<String> idEvent = new MutableLiveData<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public void setIdEvent(String idEvent) {
        this.idEvent.setValue(idEvent);
    }

    public MutableLiveData<String> getIdEvent() {
        return idEvent;
    }

    public void setVisitedEvent(VisitedEvent visitedEvent) {
        this.visitedEvent.setValue(visitedEvent);
    }

    public MutableLiveData<VisitedEvent> getVisitedEvent() {
        return visitedEvent;
    }

    public void GetDataFromDataBase(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = database.getReference("users/").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot VisitedEvent : dataSnapshot.child("VisitedEvents").getChildren()){
                    Log.d("Mytest", "viewmodel_getIdEvent " + VisitedEvent.getValue(String.class));
                    setIdEvent(VisitedEvent.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обработка ошибок
            }
        });
    }
    public void GetVisitedEvent(String idEvent){
        Log.d("Mytest", idEvent);
        DatabaseReference userRef = database.getReference("users/");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot user : snapshot.getChildren()){
                    for(DataSnapshot CreatedEvent : user.child("CreatedEvents").getChildren()){
                        if (idEvent.equals(CreatedEvent.getKey())){
                            String title = CreatedEvent.child("topic").getValue(String.class);
                            String uri = CreatedEvent.child("imageEvent").getValue(String.class);
                            VisitedEvent visitedEvent = new VisitedEvent(title, uri, idEvent);
                            Log.d("Mytest", "viewmodel" + visitedEvent.getTitle());
                            setVisitedEvent(visitedEvent);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        userRef.child(userId).child("CreatedEvents").child(idEvent).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String title = snapshot.child("topic").getValue(String.class);
//                String uri = snapshot.child("imageEvent").getValue(String.class);
//                VisitedEvent visitedEvent = new VisitedEvent(title, uri, idEvent);
//                Log.d("Mytest", "viewmodel" + visitedEvent.getTitle());
//                setVisitedEvent(visitedEvent);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
}
