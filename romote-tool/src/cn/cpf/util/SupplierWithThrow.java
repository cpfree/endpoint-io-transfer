package cn.cpf.util;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * @date 2020/11/16 17:06
 **/
@FunctionalInterface
public interface SupplierWithThrow<T, E extends Throwable> {

    T get() throws E;
}
