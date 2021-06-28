package cn.cpf.app.global;

import com.github.cosycode.common.ext.hub.Throws;
import com.github.cosycode.common.util.io.PropsUtil;
import com.github.cosycode.ext.swing.comp.JField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * <b>Description : </b> 管理组件的类
 * <p>
 * <b>created in </b> 2021/5/25
 *
 * @author CPF
 * @since 1.0
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompContext {

    private static String lang = "en";

    private static Properties langProperties = null;

    private static List<CompBean<?>> compBeanList = null;

    public static String getLangString(String key) {
        if (langProperties == null) {
            langProperties = Throws.fun("suitpath:lang/" + lang + ".properties", PropsUtil::loadProps).runtimeExp().value();
        }
        return langProperties.getProperty(key);
    }

    /**
     * 组件绑定类, 用于封装绑定的组件
     *
     * @param <T>
     */
    @AllArgsConstructor
    public abstract static class CompBean<T extends JComponent> {
        public CompBean() {
            this.key = null;
            this.component = null;
        }

        public static String getLangString(String key) {
            return CompContext.getLangString(key);
        }

        public final String key;

        public final T component;

        public abstract void check();
    }

    /**
     * 将 key 和组件 comp 绑定起来, 方便对 comp 的行为的统一管理.
     *
     * @param key 组建绑定的key
     * @param comp 创建的组件
     * @param <T> 组件类型
     * @return comp
     */
    public static <T extends JComponent> T registerComponent(String key, T comp) {
        if (comp instanceof AbstractButton) {
            CompBean<AbstractButton> jButtonCompBean = new CompBean<AbstractButton>(key, (AbstractButton) comp) {
                public void check() {
                    String text = getLangString(key);
                    component.setText(StringUtils.isBlank(text) ? key : text);
                    Optional.ofNullable(getLangString(key + ".tip")).filter(StringUtils::isNotBlank).ifPresent(component::setToolTipText);
                }
            };
            register(jButtonCompBean);
        } else if (comp instanceof JField) {
            CompBean<JField> jFieldCompBean = new CompBean<JField>(key, (JField) comp) {
                public void check() {
                    String text = getLangString(key);
                    component.getJLabel().setText(StringUtils.isBlank(text) ? key : text);
                    Optional.ofNullable(getLangString(key + ".tip")).filter(StringUtils::isNotBlank).ifPresent(component::setToolTipText);
                }
            };
            register(jFieldCompBean);
        }
        return comp;
    }

    /**
     * 注册组件
     */
    public static <T extends JComponent> void register(@NonNull CompBean<T> jButtonCompBean) {
        if (compBeanList == null) {
            compBeanList = new ArrayList<>();
        }
        compBeanList.add(jButtonCompBean);
    }

    /**
     * 改变组件的语言
     *
     * @param string 语言标记
     */
    public static void changeLang(String string) {
        lang = string;
        langProperties = null;
        compBeanList.forEach(CompBean::check);
    }

}
