package cn.cpf.app.lattice;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * @date 2020/11/18 11:40
 **/
public class Chain<T> {

    private Chain() {}

    public static Chain instance() {
        return new Chain();
    }

    private List<Predicate<T>> list;

    public void then(Predicate<T> predicate) {
        list.add(predicate);
    }


}
