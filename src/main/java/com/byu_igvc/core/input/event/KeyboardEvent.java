package com.byu_igvc.core.input.event;

import com.byu_igvc.core.input.event.IEvent;

public class KeyboardEvent extends IEvent {
    public enum EventType {PRESSED, RELEASED, TYPED}
    private int key;
    private EventType eventType;

    public KeyboardEvent(EventType type, int key) {
        this.eventType = type;
        this.key = key;
    }

    public EventType getType() {
        return eventType;
    }

    public int getKey() {
        return key;
    }
}
