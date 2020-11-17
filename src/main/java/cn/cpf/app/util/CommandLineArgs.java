package cn.cpf.app.util;

import java.util.*;

/**
 * <b>Description : </b> 用于命令行参数的解析
 *
 * @author CPF
 * @date 2020/11/13 10:00
 **/
public class CommandLineArgs {

    private List<String> noKeyParams;
    private Map<String, String> keyParams;
    private boolean strict;

    private CommandLineArgs(String[] args) {
        for (int i = 0, len = args.length; i < len; i++) {
            final String arg = args[i];
            if ('-' == arg.charAt(0)) {
                if (i + 1 >= len && strict) {
                    throw new IllegalArgumentException("strict modal: program arguments ia black");
                }
                getKeyParams().put(arg.replaceFirst("-+", "").toLowerCase(), args[++i]);
            } else {
                getNoKeyParams().add(arg);
            }
        }
    }

    public static CommandLineArgs analyze(String[] args) {
        return new CommandLineArgs(args);
    }

    public static CommandLineArgs strictAnalyze(String[] args) {
        requireNonEmpty(args);
        return new CommandLineArgs(args);
    }

    public static void requireNonEmpty(String[] args){
        requireNonEmpty(args, null);
    }

    public static void requireNonEmpty(String[] args, String msg){
        if (args == null || args.length < 1) {
            if (msg == null || msg.trim().length() == 0) {
                throw new IllegalArgumentException("program arguments ia black");
            } else {
                throw new IllegalArgumentException(msg);
            }
        }
    }

    public String getKeyVal(String key) {
        if (keyParams == null) {
            return "";
        }
        return keyParams.get(key.toLowerCase());
    }

    public String getDefaultKeyVal(String key, String defaultVal) {
        final String keyVal = getKeyVal(key);
        return keyVal.equals("") ? defaultVal : keyVal;
    }

    public int getDefaultKeyVal(String key, int defaultVal) {
        if (keyParams == null) {
            return defaultVal;
        }
        final String keyVal = getKeyVal(key);
        return keyVal.equals("") ? defaultVal : Integer.parseInt(keyVal);
    }

    public List<String> getNoKeyParams() {
        return Optional.ofNullable(noKeyParams).orElse(new ArrayList<>());
    }

    public Map<String, String> getKeyParams() {
        return Optional.ofNullable(keyParams).orElse(new HashMap<>());
    }

}
