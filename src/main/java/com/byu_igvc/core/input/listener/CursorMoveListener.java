package com.byu_igvc.core.input.listener;

import com.byu_igvc.core.input.event.CursorMoveEvent;

public abstract class CursorMoveListener implements IListener<CursorMoveEvent> {
    @Override
    public void handleEvent(CursorMoveEvent event) {
        cursorMove(event.getxDelta(), event.getyDelta());
    }

    public abstract void cursorMove(double x, double y);
}
