package com.byu_igvc.logger;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Provides an abstraction to System.out.print that will format the output for better logging
 * Uses UNICODE to format the output
 * TODO Add functionality to record logs
 * TODO Add custom color scheming
 */
public class Logger {
    private static LogSaver logSaver;
    private static boolean logClass = false;

    public static void setLogSaver(LogSaver saver) {
        logSaver = saver;
    }

    /**
     * Log levels, logs can be filtered by levels
     */
    public enum LEVEL {FINE, INFO, PASS, FAIL, HEAD ,WARN, ERROR, SEVERE}

    /**
     * Color enums, all possible colors with ascii escape codes
     */
    public enum COLOR {WHITE, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN, BLACK, NULL}

    /**
     * Formatting enum, lets you do a bunch of cool formats
     */
    public enum FORMATS {ITALIC, BOLD, RESET, UNDERLINE, STRIKETHROUGH}

    /**
     * This is the level that the logs are filtered by
     */
    private static LEVEL logLevel = LEVEL.INFO;

    /**
     * On some logs, you will want to add a stack trace, but don't want to print it
     */
    private static boolean shouldPrintStackTrace = false;

    /**
     * Most basic log command
     *
     * @param level   level of log
     * @param message message to display
     */
    public static void log(LEVEL level, String message) {
        log(level, message, true);
    }

    /**
     * Interprets each level to create a visually enticing output
     *
     * @param level   level
     * @param message message to display
     * @param fancy   fancy output or not
     */
    public static void log(LEVEL level, String message, boolean fancy) {
        if (!fancy) {
            log(level, message, COLOR.WHITE, COLOR.NULL, COLOR.NULL, COLOR.NULL);
            return;
        }
        switch (level) {
            case FINE:
                log(level, message, COLOR.NULL, COLOR.NULL, COLOR.NULL, COLOR.NULL);
                break;
            case HEAD:
                log(level, message, COLOR.CYAN, COLOR.NULL, COLOR.CYAN, COLOR.NULL);
                break;
            case INFO:
                log(level, message, COLOR.NULL, COLOR.NULL, COLOR.NULL, COLOR.NULL);
                break;
            case WARN:
                log(level, message, COLOR.YELLOW, COLOR.NULL, COLOR.YELLOW, COLOR.NULL);
                break;
            case PASS:
                log(level, message, COLOR.NULL, COLOR.GREEN, COLOR.GREEN, COLOR.NULL);
                break;
            case FAIL:
                log(level, message, COLOR.NULL, COLOR.RED, COLOR.RED, COLOR.NULL);
                break;
            case ERROR:
                log(level, message, COLOR.RED, COLOR.NULL, COLOR.RED, COLOR.NULL);
                break;
            case SEVERE:
                log(level, message, COLOR.RED, COLOR.NULL, COLOR.RED, COLOR.NULL);
                break;
        }
    }

    /**
     * Creates a log output that is colored
     *
     * @param level      the level of the log
     * @param message    message to display
     * @param fg_tag     color of the level tag e.g. [INFO]
     * @param bg_tag     background of level tag
     * @param fg_message message foreground color
     * @param bg_message message background color
     */
    public static void log(LEVEL level, String message, COLOR fg_tag, COLOR bg_tag, COLOR fg_message, COLOR bg_message) {
        if (level.ordinal() >= logLevel.ordinal()) {
            Calendar calendar = Calendar.getInstance();
            String timestamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:mmm").format(calendar.getTime());
            System.out.println(format(fg_message, bg_message) + "[" + timestamp + "]" + format(fg_tag, bg_tag, FORMATS.BOLD) + "[" + level.name() + "]" + format(fg_message, bg_message) + " " + message + format(COLOR.NULL, COLOR.NULL));
        }
    }

    /**
     * This is a info log
     * @param message message to display
     */
    public static void info(String message) {
        if (logClass)
            message = modifyMessageIncludeClass(Arrays.asList(Thread.currentThread().getStackTrace()), message);

        log(LEVEL.INFO, message);
        saveLog(LEVEL.INFO, message);
    }
    public static void info(Class<?> tClass, String message) {
        message = modifyMessageAddClass(tClass, message);
        info(message);
    }

    /**
     * This is a fine log, log for a lot of random output
     * @param message message to display
     */
    public static void fine(String message) {
        if (logClass)
            message = modifyMessageIncludeClass(Arrays.asList(Thread.currentThread().getStackTrace()), message);

        log(LEVEL.FINE, message);
//        saveLog(LEVEL.FINE, message);
    }
    public static void fine(Class<?> tClass, String message) {
        message = modifyMessageAddClass(tClass, message);
        fine(message);
    }

    /**
     * This is a warn log
     * @param message message to display
     */
    public static void warn(String message) {warn(message, null);}
    public static void warn(String message, Exception e) {
        if (logClass)
            message = modifyMessageIncludeClass(Arrays.asList(Thread.currentThread().getStackTrace()), message);

        if (shouldPrintStackTrace && e != null)
            e.printStackTrace();
        log(LEVEL.WARN, message);
        saveLog(LEVEL.WARN, message, e);
    }
    public static void warn(Class<?> tClass, String message, Exception e) {
        message = modifyMessageAddClass(tClass, message);
        warn(message, e);
    }
    public static void warn(Class<?> tClass, String message) {warn(tClass, message, null);}

    /**
     * This is a error log
     * @param message message to display
     */
    public static void error(String message, Exception e) {
        if (logClass)
            message = modifyMessageIncludeClass(Arrays.asList(Thread.currentThread().getStackTrace()), message);

        if (shouldPrintStackTrace && e != null)
            e.printStackTrace();

        log(LEVEL.ERROR, message);
        saveLog(LEVEL.ERROR, message, e);
    }
    public static void error(Class<?> tClass, String message, Exception e) {
        message = modifyMessageAddClass(tClass, message);
        error(message, e);
    }
    public static void error(Class<?> tClass, String message) {error(tClass, message, null);}

    public static void severe(String message, Exception e) {
        if (logClass)
            message = modifyMessageIncludeClass(Arrays.asList(Thread.currentThread().getStackTrace()), message);

        if (shouldPrintStackTrace && e != null)
            e.printStackTrace();

        log(LEVEL.SEVERE, message);
        saveLog(LEVEL.SEVERE, message, e);
    }
    public static void severe(Class<?> tClass, String message, Exception e) {
        message = modifyMessageAddClass(tClass, message);
        severe(message, e);
    }
    public static void severe(Class<?> tClass, String message) {severe(tClass, message, null);}

    /**
     * This is a pass log
     * @param message message to display
     */
    public static void pass(String message) {
        if (logClass)
            message = modifyMessageIncludeClass(Arrays.asList(Thread.currentThread().getStackTrace()), message);

        saveLog(LEVEL.PASS, message);
        log(LEVEL.PASS, message);
    }
    public static void pass(Class<?> tClass, String message) {
        message = modifyMessageAddClass(tClass, message);
        pass(message);
    }

    /**
     * This is a fail log
     * @param message message to display
     */
    public static void fail(String message) {
        if (logClass)
            message = modifyMessageIncludeClass(Arrays.asList(Thread.currentThread().getStackTrace()), message);

        saveLog(LEVEL.FAIL, message);
        log(LEVEL.FAIL, message);
    }
    public static void fail(Class<?> tClass, String message) {
        message = modifyMessageAddClass(tClass, message);
        fail(message);
    }

    /**
     * This is a header log
     * @param message message to display
     */
    public static void head(String message) {
        if (logClass)
            message = modifyMessageIncludeClass(Arrays.asList(Thread.currentThread().getStackTrace()), message);

        saveLog(LEVEL.HEAD, message);
        log(LEVEL.HEAD, message);
    }
    public static void head(Class<?> tClass, String message) {
        message = modifyMessageAddClass(tClass, message);
        head(message);
    }

    /**
     * This uses the stack trace to get the class that called the logger
     * @param stackTrace Thread stack trace, will filter Thread and Logger classes from it
     * @param message message to modify
     * @return the modified message
     */
    public static String modifyMessageIncludeClass(List<StackTraceElement> stackTrace, String message) {
        StackTraceElement lastElement = null;
        for (StackTraceElement element : stackTrace) {
            if (!element.getClassName().toLowerCase().contains("logger") && !element.getClassName().toLowerCase().contains("thread")) {
                lastElement = element;
                break;
            }
        }
        String className = null;
        try {
            Class<?> tClass = null;
            if (lastElement != null) {
                tClass = Class.forName(lastElement.getClassName());
                className = tClass.getSimpleName();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder str = new StringBuilder();
        str.append("[").append(className).append("] ").append(message);
        return str.toString();
    }

    /**
     * Adds a class tag to the message string
     * @param tClass class to add to message
     * @param message message to modify
     * @return modified message
     */
    public static String modifyMessageAddClass(Class<?> tClass, String message) {
        String className = tClass.getSimpleName();
        StringBuilder str = new StringBuilder();
        str.append("[").append(className).append("] ").append(message);
        return str.toString();
    }

    /**
     * Print a new line
     */
    public static void line() {
        System.out.print('\n');
    }

    public static boolean isShouldPrintStackTrace() {
        return shouldPrintStackTrace;
    }

    public static void setShouldPrintStackTrace(boolean shouldPrintStackTrace) {
        Logger.shouldPrintStackTrace = shouldPrintStackTrace;
    }

    /**
     * Generates a string that with escape codes to apply a foreground and background color
     *
     * @param foreground The desired color
     * @param background background color
     * @param formats    list of formats that must be applied to the string
     * @return formatted escape string
     */
    public static String format(COLOR foreground, COLOR background, FORMATS... formats) {
        // Escape key
        StringBuilder str = new StringBuilder("\u001B");
        // Add foreground color
        switch (foreground) {
            case WHITE:
                str.append("[37");
                break;
            case RED:
                str.append("[31");
                break;
            case GREEN:
                str.append("[32");
                break;
            case YELLOW:
                str.append("[33");
                break;
            case BLUE:
                str.append("[34");
                break;
            case MAGENTA:
                str.append("[35");
                break;
            case CYAN:
                str.append("[36");
                break;
            case BLACK:
                str.append("[37");
                break;
            default:
                str.append("[0");
                break;
        }
        // Add formats
        for (FORMATS format : formats) {
            str.append(';');
            switch (format) {
                case BOLD:
                    str.append('1');
                    break;
                case ITALIC:
                    str.append('3');
                    break;
                case UNDERLINE:
                    str.append('4');
                    break;
                case STRIKETHROUGH:
                    str.append('9');
                    break;
            }
        }
        // Add background color
        if (background != COLOR.NULL) {
            str.append(';');
            switch (background) {
                case WHITE:
                    str.append("47");
                    break;
                case RED:
                    str.append("41");
                    break;
                case GREEN:
                    str.append("42");
                    break;
                case YELLOW:
                    str.append("43");
                    break;
                case BLUE:
                    str.append("44");
                    break;
                case MAGENTA:
                    str.append("45");
                    break;
                case CYAN:
                    str.append("46");
                    break;
                case BLACK:
                    str.append("47");
                    break;
            }
        }
        // Close the escape sequence
        str.append("m");
        return str.toString();
    }

    public static LEVEL getLogLevel() {
        return logLevel;
    }
    public static void setLogLevel(LEVEL logLevel) {
        Logger.logLevel = logLevel;
    }

    public static void setUpLogSaver() {
        if (logSaver == null)
            logSaver = new LogSaver("logs");
    }

    private static void saveLog(LEVEL level, String message, Exception e) {
        if (logSaver != null)
            logSaver.log(level, message, e);
    }
    private static void saveLog(LEVEL level, String message) {
        saveLog(level, message, null);
    }

    /**
     * Flushes the logger to make sure all the logs are saved
     */
    public static void flush() {
        if (logSaver != null)
            logSaver.flush();
    }
    public static LogSaver getLogSaver() {
        return logSaver;
    }

    public static boolean isLogClass() {
        return logClass;
    }
    public static void setLogClass(boolean logClass) {
        Logger.logClass = logClass;
    }

    public static void enter(Class<?> tClass, Method method) {
        fine("Entering CLASS: " + tClass.getSimpleName() + ", METHOD: " + method.getName());
    }

    public static void exit(Class<?> tClass, Method method) {
        fine("Entering CLASS: " + tClass.getSimpleName() + ", METHOD: " + method.getName());
    }
}
