package com.byu_igvc.logger;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogSaver {
    private Path path;
    private Deque<String> messageQueue;
    private String timestamp;
    private static boolean logging = false;

    public LogSaver(String path) {
        this.path = Paths.get(path);
        messageQueue = new ArrayDeque<>();

        Calendar calendar = Calendar.getInstance();
        timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(calendar.getTime());
        File file = new File(path + "/" + timestamp + ".txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String exceptionStacktraceToString(Exception e)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        ps.close();
        return baos.toString();
    }

    public void log(Logger.LEVEL level, String message) {
        log(level, message, null);
    }

    public void log(Logger.LEVEL level, String message, Exception e) {
        Calendar calendar = Calendar.getInstance();
        String timestamp = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss:mmm").format(calendar.getTime());
        String log = "[" + timestamp + "] [" + level.name() + "]: " + message;

        messageQueue.add(log);
        if (e != null)
            messageQueue.add(exceptionStacktraceToString(e));

        if (messageQueue.size() > 10 && !logging)
            new Thread(this::writeLogQueueToFile).start();
    }

    private void writeLogQueueToFile() {
        logging = true;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(path + "/" + timestamp + ".txt"), true));
            PrintWriter printWriter = new PrintWriter(writer);
            while (!messageQueue.isEmpty())
                printWriter.println(messageQueue.pop());
            writer.close();
        } catch (IOException e) {
            Logger.warn("Missed a log, to much logging taking place");
        }
        logging = false;
    }

    public void flush() {
        writeLogQueueToFile();
    }
}
