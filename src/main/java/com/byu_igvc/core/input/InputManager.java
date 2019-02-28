package com.byu_igvc.core.input;

import com.byu_igvc.core.input.event.IEvent;
import com.byu_igvc.core.input.event.KeyboardEvent;
import com.byu_igvc.core.input.event.MouseButtonEvent;
import com.byu_igvc.core.input.listener.IListener;
import com.byu_igvc.core.input.listener.KeyboardListener;
import com.byu_igvc.core.input.listener.MouseButtonListener;
import com.byu_igvc.core.render.IEngine;

import com.byu_igvc.core.render.OpenGLRenderingEngine;
import com.byu_igvc.logger.Logger;
import org.lwjgl.glfw.*;

import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager implements IEngine {
    private HashMap<Class<? extends IEvent>, List<IListener<IEvent>>> listeners;

    public InputManager() {
        listeners = new HashMap<>();
    }

    @Override
    public void init() {
        GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scanCode, int action, int mods) {
                if (action == GLFW_PRESS) {
                    triggerEvent(KeyboardEvent.class, new KeyboardEvent(KeyboardEvent.EventType.PRESSED, key));
                } else if (action == GLFW_RELEASE) {
                    triggerEvent(KeyboardEvent.class, new KeyboardEvent(KeyboardEvent.EventType.RELEASED, key));
                }
            }
        };
        glfwSetKeyCallback(OpenGLRenderingEngine.getWindow(), keyCallback);
        GLFWMouseButtonCallback mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                triggerEvent(MouseButtonEvent.class, new MouseButtonEvent(button, MouseButtonEvent.EventType.values()[action]));
            }
        };
        glfwSetMouseButtonCallback(OpenGLRenderingEngine.getWindow(), mouseButtonCallback);
        GLFWCursorPosCallback cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xDelta, double yDelta) {

            }
        };
        glfwSetCursorPosCallback(OpenGLRenderingEngine.getWindow(), cursorPosCallback);
        glfwSetCursorEnterCallback(OpenGLRenderingEngine.getWindow(), new GLFWCursorEnterCallback() {
            @Override
            public void invoke(long window, boolean entered) {

            }
        });
    }

    @Override
    public void shutdown() {

    }

    /**
     * Gets the events that are of the same type
     * @param event event to be triggered
     */
    private void triggerKeyboardEvent(KeyboardEvent event) {
        if (listeners.containsKey(KeyboardListener.class))
            for (IListener<IEvent> listener : listeners.get(KeyboardListener.class))
                    listener.handleEvent(event);
    }

    private <T extends IEvent> void triggerEvent(Class<T> tClass, T event) {
        if (listeners.containsKey(tClass))
            for (IListener<IEvent> listener : listeners.get(tClass))
                listener.handleEvent(event);
    }

    public <T extends IListener, U extends IEvent> void registerListener(Class<U> tClass, T t) {
        if (!listeners.containsKey(tClass))
            listeners.put(tClass, new ArrayList<>());

        listeners.get(tClass).add(t);
    }
}
