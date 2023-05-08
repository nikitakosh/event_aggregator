package com.example.event_aggregator2.ui.profile;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<String> titleCreatedEvent = new MutableLiveData<>();
    private MutableLiveData<String> photoCreatedEvent = new MutableLiveData<>();
    private MutableLiveData<String> titleVisitedEvent = new MutableLiveData<>();
    private MutableLiveData<String> photoVisitedEvent = new MutableLiveData<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private MutableLiveData<String> profilePhotoUri = new MutableLiveData<>();
    private MutableLiveData<String> NameSurname = new MutableLiveData<>();
    private MutableLiveData<String> City = new MutableLiveData<>();
    private MutableLiveData<String> AboutYourself = new MutableLiveData<>();
    private MutableLiveData<Boolean> IsOrganizer = new MutableLiveData<>();

    public MutableLiveData<String> getTitleVisitedEvent() {
        return titleVisitedEvent;
    }

    public void setTitleVisitedEvent(String titleVisitedEvent) {
        this.titleVisitedEvent.setValue(titleVisitedEvent);
    }

    public void setPhotoVisitedEvent(String photoVisitedEvent) {
        this.photoVisitedEvent.setValue(photoVisitedEvent);
    }

    public MutableLiveData<String> getPhotoVisitedEvent() {
        return photoVisitedEvent;
    }

    public void setTitleCreatedEvent(String titleCreatedEvent) {
        this.titleCreatedEvent.setValue(titleCreatedEvent);
    }

    public MutableLiveData<String> getTitleCreatedEvent() {
        return titleCreatedEvent;
    }

    public MutableLiveData<String> getPhotoCreatedEvent() {
        return photoCreatedEvent;
    }

    public void setPhotoCreatedEvent(String photoCreatedEvent) {
        this.photoCreatedEvent.setValue(photoCreatedEvent);
    }

    public MutableLiveData<String> getProfilePhotoUri() {
        return profilePhotoUri;
    }

    public void setProfilePhotoUri(String profilePhotoUri) {
        this.profilePhotoUri.setValue(profilePhotoUri);
    }

    public void setIsOrganizer(boolean IsOrganizer) {
        this.IsOrganizer.setValue(IsOrganizer);
    }

    public MutableLiveData<Boolean> getIsOrganizer() {
        return IsOrganizer;
    }

    public void setNameSurname(String NameSurname) {
        this.NameSurname.setValue(NameSurname);
    }
    public void setCity(String City) {
        this.City.setValue(City);
    }
    public void setAboutYourself(String AboutYourself) {
        this.AboutYourself.setValue(AboutYourself);
    }

    public MutableLiveData<String> getNameSurname() {
        return NameSurname;
    }
    public MutableLiveData<String> getCity() {
        return City;
    }
    public MutableLiveData<String> getAboutYourself() {
        return AboutYourself;
    }

    public void GetDataFromDataBase(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = database.getReference("users/" + userId);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Mytest", "GetDataFromDataBase");
                String name = snapshot.child("name").getValue(String.class);
                String surname = snapshot.child("surname").getValue(String.class);
                String city = snapshot.child("city").getValue(String.class);
                String AboutYourself = snapshot.child("AboutYourself").getValue(String.class);
                boolean IsOrganizer = Boolean.TRUE.equals(snapshot.child("IsOrganizer").getValue(Boolean.class));
                setIsOrganizer(IsOrganizer);
                setNameSurname(name + " " + surname);
                setAboutYourself(AboutYourself);
                setCity(city);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Mytest", "getUser:onCancelled", error.toException());
            }
        });
    }
    public void GetProfileImageFromDataBase(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setProfilePhotoUri(snapshot.child("profileImage").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void LoadOrganizedEventFromDataBase(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = database.getReference("users/").child(userId).child("CreatedEvents");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    for(DataSnapshot CreatedEvent : snapshot.getChildren()){
                        setTitleCreatedEvent(CreatedEvent.child("topic").getValue(String.class));
                        setPhotoCreatedEvent(CreatedEvent.child("imageEvent").getValue(String.class));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void LoadVisitedEventFromDataBase(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = database.getReference("users/").child(userId).child("VisitedEvents");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    for(DataSnapshot VisitedEvent : snapshot.getChildren()){
                        DatabaseReference VisitedEventRef = database.getReference("users/");
                        VisitedEventRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    for(DataSnapshot CreatedEvent : userSnapshot.child("CreatedEvents").getChildren()){
                                        if (VisitedEvent.getValue(String.class).equals(CreatedEvent.getKey())){
                                            String title = CreatedEvent.child("topic").getValue(String.class);
                                            String imageEvent = CreatedEvent.child("imageEvent").getValue(String.class);
                                            setTitleVisitedEvent(title);
                                            setPhotoVisitedEvent(imageEvent);
                                            break;
                                        }
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Log.d("Mytest", "Load " + VisitedEvent.getValue(String.class));
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
