package cn.cpf.app.global;

import com.github.cosycode.common.ext.hub.Throws;
import com.github.cosycode.common.util.io.PropsUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.BiConsumer;

/**
 * <b>Description : </b> 管理组件的类
 * <p>
 * <b>created in </b> 2021/5/25
 *
 * @author CPF
 * @since 1.0
 **/
@NoArgsConstructor
public class CompContext {

    private static String lang = "en";

    public static Properties langProperties = null;

    public static List<CompBean<?>> compBeanList = null;

    public static String getLangString(String key) throws IOException {
        if (langProperties == null) {
            langProperties = PropsUtil.loadProps(lang + ".properties");
        }
        return langProperties.getProperty(key, key);
    }

    @AllArgsConstructor
    static class CompBean<T extends JComponent> {
        public final String key;
        public final T component;
        public final BiConsumer<String, T> biConsumer;
        public void check() {
            biConsumer.accept(key, component);
        }
    }

    public static final BiConsumer<String, JButton> buttonBiConsumer = (key, button) -> {
        Throws.run(() -> {
            String text = getLangString(key);
            button.setText(text);
            Optional.ofNullable(getLangString(key + ".tip")).ifPresent(button::setToolTipText);
        });
    };

    public static <T extends JComponent> void register(String key, T component) {
        if (component instanceof JButton) {
            register(key, (JButton)component, buttonBiConsumer);
        } else {
            register(key, component, null);
        }
    }

    public static <T extends JComponent> void register(String key, T component, BiConsumer<String, T> biConsumer) {
        if (compBeanList == null) {
            compBeanList = new ArrayList<>();
        }
        compBeanList.add(new CompBean<>(key, component, biConsumer));
    }

    public static void changeLang(String string) {
        lang = string;
        langProperties = null;
        compBeanList.forEach(CompBean::check);
    }

}
