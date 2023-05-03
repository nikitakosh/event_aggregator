package com.example.event_aggregator2.ui.organizedEvents;

import android.net.Uri;

import com.example.event_aggregator2.ui.event.EventFragment;

public class OrganizedEvent {
    private String title;
    private String eventUri;
    public OrganizedEvent(String title, String eventUri){
        this.title = title;
        this.eventUri = eventUri;
    }

    public void setEventUri(String eventUri) {
        this.eventUri = eventUri;
    }

    public String getEventUri() {
        return eventUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
