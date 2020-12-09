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
    private static int flag;

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
        if (flag > 0) {
            switch (level) {
                case DEBUG:
                    log.debug(s, objects);
                    break;
                case INFO:
                    log.info(s, objects);
                    break;
                case WARN:
                    log.warn(s, objects);
                    break;
                case ERROR:
                    log.error(s, objects);
                    break;
                default:
            }
        } else {
            System.out.println(level.name() + " ==> " + StrUtils.format(s, objects));
        }
    }

}
