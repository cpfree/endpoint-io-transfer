package cn.cpf.app.inter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * <b>Description : </b> 将传入的 Consumer<T> then 作为 被代理函数式接口实例 传入, 将该对象的 onceExe 方法作为 代理方法实例 返回
 * <p> 逻辑: 完成 函数式接口实例 的 异步单线程 代理
 * <b>created in </b> 2021/4/14
 *
 * @author CPF
 * @since 1.0
 **/
public class OnceExecutorForConsumer<T> {

    private final Lock lock = new ReentrantLock();

    /**
     * 被代理的方法
     */
    private final Consumer<T> then;

    private Consumer<T> skip;

    public OnceExecutorForConsumer(Consumer<T> then) {
        this.then = then;
    }

    /**
     * 代理方法
     *
     * <p>
     * 每次调用新建一个线程对参数进行处理, 主线程不阻塞, 分线程竞争锁, 抢到运行 then, 抢不到运行 skip
     * </p>
     */
    public void onceExe(final T e) {
        new Thread(() -> {
            if (lock.tryLock()) {
                try {
                    if (then != null) {
                        then.accept(e);
                    }
                } finally {
                    lock.unlock();
                }
            } else {
                if (skip != null) {
                    skip.accept(e);
                }
            }
        }).start();
    }

    public OnceExecutorForConsumer<T> setSkip(Consumer<T> skip) {
        this.skip = skip;
        return this;
    }

}
