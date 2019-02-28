package com.byu_igvc.core.input.event;

public abstract class IEvent {
    /**
     * This determines whether or not an event was acted upon
     * This is useful to prevent all listener's from triggering, ei GUI with layered buttons
     */
    private boolean isConsumed;

    public void consume() {
        this.isConsumed = true;
    }

    public boolean isConsumed() {
        return isConsumed;
    }
}
