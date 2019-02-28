package com.byu_igvc.core.input.listener;

import com.byu_igvc.core.input.event.KeyboardEvent;

public abstract class KeyboardListener implements IListener<KeyboardEvent> {
    @Override
    public void handleEvent(KeyboardEvent event) {
        switch (event.getType()) {
            case TYPED:
                keyTyped(event.getKey());
                break;
            case PRESSED:
                keyPressed(event.getKey());
                break;
            case RELEASED:
                keyReleased(event.getKey());
                break;
        }
    }

    public abstract void keyPressed(int key);
    public abstract void keyReleased(int key);
    public abstract void keyTyped(int key);
}
