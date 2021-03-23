package cn.cpf.app.global;

import com.github.cosycode.common.ext.hub.SimpleCode;
import com.github.cosycode.common.util.io.PropsUtil;

import java.awt.*;
import java.io.IOException;
import java.util.Properties;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * @date 2020/12/21 18:28
 **/
public class ConfigHelper {

    private ConfigHelper(){}

    private static Properties getProperties() throws IOException {
        return PropsUtil.loadProps("classpath:conf.properties");
    }

    public static String getSavePath() {
        return SimpleCode.runtimeException(ConfigHelper::getProperties).getProperty("saveDirPath");
    }

    public static String getScanSaveDirPath() {
        return SimpleCode.runtimeException(ConfigHelper::getProperties).getProperty("scanSaveDirPath");
    }

    public static Color getDefaultColor() {
        return Color.lightGray;
    }

}
