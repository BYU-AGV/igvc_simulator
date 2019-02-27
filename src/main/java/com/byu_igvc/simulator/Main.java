package com.byu_igvc.simulator;

import com.byu_igvc.logger.LogSaver;
import com.byu_igvc.logger.Logger;

public class Main {
    public static void main(String...args) {
        initLogger();
        Logger.fine("Setup logger");
    }

    private static void initLogger() {
        Logger.setShouldPrintStackTrace(true);
        Logger.setLogClass(true);
        Logger.setLogLevel(Logger.LEVEL.FINE);
        Logger.setLogSaver(new LogSaver("logs"));
    }
}
