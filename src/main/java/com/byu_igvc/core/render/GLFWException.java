package com.byu_igvc.core.render;

public class GLFWException extends Exception {
    public GLFWException(int errorCode, long errorMessage) {
    }

    public GLFWException(String message) {
        super(message);
    }

    public GLFWException(int errorCode, String message) {
        super("Error: " + errorCode + ", message: " + message);
    }
}
