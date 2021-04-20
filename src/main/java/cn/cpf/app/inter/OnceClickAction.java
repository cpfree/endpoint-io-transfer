package cn.cpf.app.inter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.ActionListener;

/**
 * <b>Description : </b>
 * <p>
 * <b>created in </b> 2021/4/14
 *
 * @author CPF
 * @since 1.0
 **/
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OnceClickAction {

    public static ActionListener of(ActionListener then) {
        return new OnceExecutorForConsumer<>(then::actionPerformed).setSkip(e -> log.debug("多次点击无效"))::onceExe;
    }

}
