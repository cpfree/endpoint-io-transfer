package cn.cpf.app.main;

import cn.cpf.app.util.OsUtils;
import cn.cpf.app.util.BdmpOutUtils;

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

    public static final String SAVE_DIR_PATH = "E:\\res\\FMBS_DEV_BANK\\inner\\clipout\\";

    public static void main(String[] args) throws AWTException, IOException {
        final BufferedImage mainScreenShot = OsUtils.getMainScreenShot(null);
        BdmpOutUtils.convertBinPicToSource(mainScreenShot, SAVE_DIR_PATH);
    }

}
