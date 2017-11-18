package ru.kvlt.testtask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private enum LogType {
        INFO, DEBUG, WARN, ERR
    }

    private static SimpleDateFormat sdf;

    static {
        sdf = new SimpleDateFormat("[HH:mm:ss]");
    }

    public static void log(LogType type, String str) {
        String time = sdf.format(new Date());
        System.out.println(time + "[" + type + "] " + str);
    }

    public static void info(String str) {
        log(LogType.INFO, str);
    }

    public static void warn(String str) {
        log(LogType.WARN, str);
    }

    public static void debug(String str) {
        log(LogType.DEBUG, str);
    }

    public static void err(String str) {
        log(LogType.ERR, str);
    }

}
