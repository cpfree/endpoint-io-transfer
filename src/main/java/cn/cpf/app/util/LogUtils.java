package cn.cpf.app.util;

import com.github.cpfniliu.common.thread.AsynchronousProcessor;

import java.util.function.Predicate;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * @date 2020/11/17 16:31
 **/
@SuppressWarnings("java:S106")
public class LogUtils {

    private LogUtils(){}

    private static AsynchronousProcessor<String> processor = null;

    static {
        Predicate<String> disposeFun = s -> {
            System.out.println(s);
            return true;
        };
        processor = new AsynchronousProcessor<>(disposeFun, null, 0, false);
        processor.start();
    }

    public static void printDebug(String s, Object... objects) {
        processor.add(StrUtils.format(s, objects));
    }

    public static void printError(String s, Object... objects) {
        processor.add(StrUtils.format(s, objects));
    }

    public static void printWarning(String s, Object... objects) {
        processor.add(StrUtils.format(s, objects));
    }

    public static void printInfo(String s, Object... objects) {
        processor.add(StrUtils.format(s, objects));
    }

    public static void printSuccess(String s, Object... objects) {
        processor.add(StrUtils.format(s, objects));
    }

}
