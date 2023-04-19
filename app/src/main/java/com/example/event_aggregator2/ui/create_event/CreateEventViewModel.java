package com.example.event_aggregator2.ui.create_event;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateEventViewModel extends ViewModel {
    private MutableLiveData<String> address = new MutableLiveData<>();
    private MutableLiveData<String> description = new MutableLiveData<>();

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
}
