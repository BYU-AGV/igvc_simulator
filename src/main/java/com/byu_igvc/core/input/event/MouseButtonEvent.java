package com.byu_igvc.core.input.event;

public class MouseButtonEvent extends IEvent {
    private int button;
    public enum EventType {PRESSED, RELEASED}
    private EventType type;

    public MouseButtonEvent(int button, EventType type) {
        this.button = button;
        this.type = type;
    }

    public int getButton() {
        return button;
    }

    public EventType getType() {
        return type;
    }

    public EventType convertToType(int type) {
        return EventType.values()[type];
    }
}
