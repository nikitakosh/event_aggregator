package com.example.event_aggregator2.ui.event;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EventViewModel extends ViewModel {
    private MutableLiveData<Boolean> eventIsVisited = new MutableLiveData<>();
    private String idEvent;
    private String userId;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private MutableLiveData<String> photoEventUri = new MutableLiveData<>();
    private MutableLiveData<String> date = new MutableLiveData<>();
    private MutableLiveData<String> address = new MutableLiveData<>();
    private MutableLiveData<String> topic = new MutableLiveData<>();
    private MutableLiveData<String> description = new MutableLiveData<>();

    public void setEventIsVisited(boolean eventIsVisited) {
        this.eventIsVisited.setValue(eventIsVisited);
    }

    public MutableLiveData<Boolean> getEventIsVisited() {
        return eventIsVisited;
    }

    public MutableLiveData<String> getPhotoEventUri() {
        return photoEventUri;
    }

    public void setPhotoEventUri(String photoEventUri) {
        this.photoEventUri.setValue(photoEventUri);
    }

    public void setDate(String date) {
        this.date.setValue(date);
    }

    public MutableLiveData<String> getDate() {
        return date;
    }


    public void setTopic(String  topic) {
        this.topic.setValue(topic);
    }

    public MutableLiveData<String> getTopic() {
        return topic;
    }

    public void setAddress(String address) {
        this.address.setValue(address);
    }
    public void setDescription(String description) {
        this.description.setValue(description);
    }


    public MutableLiveData<String> getAddress() {
        return address;
    }
    public MutableLiveData<String> getDescription() {
        return description;
    }

    public void GetDataFromDataBase(String idEvent){
        this.idEvent = idEvent;
        DatabaseReference userRef = database.getReference("users/");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    for(DataSnapshot CreatedEvent : userSnapshot.child("CreatedEvents").getChildren()){
                        if(idEvent.equals(CreatedEvent.getKey())){
                            String address = CreatedEvent.child("address").getValue(String.class);
                            String description = CreatedEvent.child("description").getValue(String.class);
                            String topic = CreatedEvent.child("topic").getValue(String.class);
                            String date = CreatedEvent.child("date").getValue(String.class);
                            userId = userSnapshot.getKey();
                            UploadImageFromDataBase();
                            setDate(date);
                            setAddress(address);
                            setTopic(topic);
                            setDescription(description);
                        }

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void UploadImageFromDataBase(){
        DatabaseReference userRef = database.getReference("users/");
        userRef.child(userId).child("CreatedEvents").child(idEvent).child("imageEvent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uri = snapshot.getValue(String.class);
                setPhotoEventUri(uri);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void VisitEvent(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = database.getReference("users/");
        userRef.child(userId).child("VisitedEvents").push().setValue(idEvent);
    }
    public void EventIsVisited(String idEvent){
        Log.d("Mytest", "base-idEvent " + idEvent);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = database.getReference("users/").child(userId).child("VisitedEvents");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot VisitedEvent : snapshot.getChildren()){
                    Log.d("Mytest", "other " + VisitedEvent.getValue(String.class));
                    if(idEvent.equals(VisitedEvent.getValue(String.class))){
                        setEventIsVisited(true);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
