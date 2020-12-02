package cn.cpf.app.util;

import com.github.cpfniliu.common.ext.bean.DoubleBean;
import com.github.cpfniliu.common.thread.AsynchronousProcessor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;

import java.util.function.Predicate;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * @date 2020/11/17 16:31
 **/
@SuppressWarnings("java:S106")
@Slf4j
public class LogUtils {

    private LogUtils(){}

    /**
     * 1: log, 0: 控制台
     */
    @Setter
    private static int flag;

    private static AsynchronousProcessor<DoubleBean<Level, String>> processor;

    static {
        Predicate<DoubleBean<Level, String>> disposeFun = s -> {
            if (flag > 0) {
                switch (s.getO1()) {
                    case DEBUG:
                        log.debug(s.getO2());
                        break;
                    case INFO:
                        log.info(s.getO2());
                        break;
                    case WARN:
                        log.warn(s.getO2());
                        break;
                    case ERROR:
                        log.error(s.getO2());
                        break;
                    default:
                }
            } else {
                System.out.println(s.getO1().name() + " ==> " + s.getO2());
            }
            return true;
        };
        processor = new AsynchronousProcessor<>(disposeFun);
        processor.start();
    }

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

    public static void print(Level level, String s, Object... objects) {
        processor.add(DoubleBean.of(level, StrUtils.format(s, objects)));
    }

}
