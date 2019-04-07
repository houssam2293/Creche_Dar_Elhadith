package home.java;

import javafx.beans.property.SimpleStringProperty;

public class Event {
    private final SimpleStringProperty eventDescription;
    private final SimpleStringProperty eventDate;
    private final SimpleStringProperty eventTime;
    private final SimpleStringProperty eventType;

    public Event(String eventDescription, String eventDate, String eventTime, String eventType) {
        this.eventDescription = new SimpleStringProperty(eventDescription);
        this.eventDate = new SimpleStringProperty(eventDate);
        this.eventTime = new SimpleStringProperty(eventTime);
        this.eventType = new SimpleStringProperty(eventType);
    }

    public String getEventDescription() {
        return eventDescription.get();
    }

    public String getEventDate() {
        return eventDate.get();
    }


    public String getEventTime() {
        return eventTime.get();
    }

    public String getEventType() {
        return eventType.get();
    }


}
