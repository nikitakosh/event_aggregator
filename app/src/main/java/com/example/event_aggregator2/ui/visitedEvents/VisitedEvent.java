package com.example.event_aggregator2.ui.visitedEvents;

public class VisitedEvent {
    private String title;
    private String eventUri;
    private String eventId;

    public VisitedEvent(String title, String eventUri, String eventId){
        this.title = title;
        this.eventUri = eventUri;
        this.eventId = eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
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
