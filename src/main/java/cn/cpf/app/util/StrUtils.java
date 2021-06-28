package cn.cpf.app.util;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * @date 2020/11/17 15:56
 **/
public class StrUtils {

    private StrUtils() {
    }

    public static String format(String str, Object... objects) {
        if (objects == null || objects.length == 0) {
            return str;
        }
        int from = 0;
        int end;
        StringBuilder sb = new StringBuilder();
        for (Object object : objects) {
            end = str.indexOf("{}", from);
            if (end < 0) {
                break;
            }
            sb.append(str, from, end);
            sb.append(object);
            from = end + 2;
        }
        return sb.toString();
    }

}
