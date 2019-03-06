package com.byu_igvc.simulator;

import com.byu_igvc.core.render.OpenGLRenderingEngine;
import com.byu_igvc.logger.LogSaver;
import com.byu_igvc.logger.Logger;

import org.lwjgl.*;

import java.awt.*;

public class Main {
    private long window;
    private Color color;

    public static void main(String...args) {
        initLogger();
        Logger.fine("Setup logger");
        Main main = new Main();
        main.color = new Color(0x3E3C5F);
        main.run();
        Logger.flush();
    }

    private static void initLogger() {
        Logger.setShouldPrintStackTrace(true);
        Logger.setLogClass(true);
        Logger.setLogLevel(Logger.LEVEL.FINE);
        Logger.setLogSaver(new LogSaver("logs"));
    }

    public void run() {
        Logger.info("Hello from LWJGL " + Version.getVersion() + "!");
        Simulator simulator = new Simulator();
        simulator.setRenderEngine(new OpenGLRenderingEngine());
        simulator.init();
        simulator.simulate();
    }
}
