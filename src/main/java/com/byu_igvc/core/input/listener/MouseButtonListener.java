package com.byu_igvc.core.input.listener;

import com.byu_igvc.core.input.event.MouseButtonEvent;

public abstract class MouseButtonListener implements IListener<MouseButtonEvent> {
    @Override
    public void handleEvent(MouseButtonEvent event) {
        switch (event.getType()) {
            case RELEASED:
                break;
            case PRESSED:
                break;
        }
    }

    public abstract void buttonPressed(int button);
    public abstract void buttonReleased(int button);
}
