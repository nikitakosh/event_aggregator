package com.example.event_aggregator2.ui.organizedEvents;

import android.net.Uri;

import com.example.event_aggregator2.ui.event.EventFragment;

public class OrganizedEvent {
    private String title;
    private String eventUri;
    private String idEvent;
    public OrganizedEvent(String title, String eventUri, String idEvent){
        this.title = title;
        this.eventUri = eventUri;
        this.idEvent = idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public String getIdEvent() {
        return idEvent;
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
