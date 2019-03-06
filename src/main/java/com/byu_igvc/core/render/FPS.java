package com.byu_igvc.core.render;

public class FPS {
    private static float fps = 0.0f;
    private static long startTime = 0;
    private static long endTime = 0;

    public static void startFrame(){
        startTime = System.currentTimeMillis();
    }

    public static void endFrame() {
        endTime = System.currentTimeMillis();
        calculateFPS();
    }

    private static void calculateFPS() {
        fps = (float) (1.0 / ((endTime - startTime) / 1000.0));
    }

    public static float getFPS() {
        return fps;
    }
}
