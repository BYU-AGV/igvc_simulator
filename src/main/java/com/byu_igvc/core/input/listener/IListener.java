package com.byu_igvc.core.input.listener;

import com.byu_igvc.core.input.event.IEvent;

public interface IListener <T extends IEvent> {
    public void handleEvent(T event);
}
