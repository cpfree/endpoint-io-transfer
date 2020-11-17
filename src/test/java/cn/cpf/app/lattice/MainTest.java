package cn.cpf.app.lattice;

import cn.cpf.app.util.StrUtils;

/**
 *
 * java 存放字符
 *
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/8/28 11:07
 */
public class MainTest {

    public static void main(String[] args) {
//        System.out.println(MessageFormat.format("jkjkfd(0), {1}, {2}, {0}, {}", 3, false, "hello", "5454"));

        System.out.println(StrUtils.format("jkjkfd(0), {}, {2}, {}", 3, false, "hello", "5454"));
        System.out.println("".indexOf("u", 8));
    }

}
