package cn.cpf.app.util;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * @date 2020/11/17 16:31
 **/
@Slf4j
public class LogUtils {

    private LogUtils(){}

    /**
     * 1: log, 0: 控制台
     */
    @Setter
    private static boolean logFlag;

    @Setter
    private static Level logLevel = Level.DEBUG;

    public static void printDebug(String s, Object... objects) {
        print(Level.DEBUG, s, objects);
    }

    public static void printError(String s, Object... objects) {
        print(Level.ERROR, s, objects);
    }

    public static void printWarning(String s, Object... objects) {
        print(Level.WARN, s, objects);
    }

    public static void printInfo(String s, Object... objects) {
        print(Level.INFO, s, objects);
    }

    public static void printSuccess(String s, Object... objects) {
        print(Level.INFO, s, objects);
    }

    @SuppressWarnings("java:S106")
    public static void print(Level level, String s, Object... objects) {
        switch (level) {
            case DEBUG:
                if (logLevel.toInt() <= Level.DEBUG.toInt()) {
                    if (logFlag) {
                        log.debug(s, objects);
                    } else {
                        System.out.println(level.name() + " ==> " + StrUtils.format(s, objects));
                    }
                }
                break;
            case INFO:
                if (logLevel.toInt() <= Level.INFO.toInt()) {
                    if (logFlag) {
                        log.info(s, objects);
                    } else {
                        System.out.println(level.name() + " ==> " + StrUtils.format(s, objects));
                    }
                }
                break;
            case WARN:
                if (logLevel.toInt() <= Level.WARN.toInt()) {
                    if (logFlag) {
                        log.warn(s, objects);
                    } else {
                        System.out.println(level.name() + " ==> " + StrUtils.format(s, objects));
                    }
                }
                break;
            case ERROR:
                if (logLevel.toInt() <= Level.ERROR.toInt()) {
                    if (logFlag) {
                        log.error(s, objects);
                    } else {
                        System.out.println(level.name() + " ==> " + StrUtils.format(s, objects));
                    }
                }
                break;
            default:
        }
    }

}
