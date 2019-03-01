package com.byu_igvc.core.input.event;

public class CursorMoveEvent extends IEvent {
    private double xDelta, yDelta;

    public CursorMoveEvent(double xDelta, double yDelta) {
        this.xDelta = xDelta;
        this.yDelta = yDelta;
    }

    public double getxDelta() {
        return xDelta;
    }

    public double getyDelta() {
        return yDelta;
    }
}
