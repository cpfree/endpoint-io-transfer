package cn.cpf.app.main;

import cn.cpf.app.global.ConfigHelper;
import cn.cpf.app.util.BdmpOutUtils;
import cn.cpf.app.util.OsUtils;
import com.github.cosycode.common.helper.CommandLineHelper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <b>Description : </b> 命令行入口, 主屏幕截屏, 之后识别屏幕, 最后将识别后的信息存入相应位置
 *
 * @author CPF
 * @date 2020/11/17
 **/
public class BdmpDistinguishForMainScreenShot {

    public static void main(String[] args) throws AWTException, IOException {
        CommandLineHelper commandLineHelper = CommandLineHelper.parse(args);
        final String saveDirPath = commandLineHelper.getDefaultParam("p", ConfigHelper.getSavePath());
        final BufferedImage mainScreenShot = OsUtils.getMainScreenShot(null);
        BdmpOutUtils.convertBinPicToSource(mainScreenShot, saveDirPath);
    }

}
