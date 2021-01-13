package cn.cpf.app.main;

import cn.cpf.app.global.ConfigHelper;
import cn.cpf.app.util.BdmpOutUtils;
import cn.cpf.app.util.LogUtils;
import cn.cpf.app.util.OsUtils;
import com.github.cosycode.common.helper.CommandLineHelper;

import java.awt.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <b>Description : </b> 命令行入口, 将粘贴板中的图片转换为文件
 *
 * @author CPF
 * @date 2020/11/17
 **/
public class BdmpDistinguishFromClipboard {

    public static void main(String[] args) throws IOException, UnsupportedFlavorException {
        CommandLineHelper commandLineHelper = CommandLineHelper.parse(args);
        final String saveDirPath = commandLineHelper.getDefaultParam("p", ConfigHelper.getSavePath());
        final Image imageFromClipboard = OsUtils.getImageFromClipboard();
        if (imageFromClipboard == null) {
            LogUtils.printWarning("将粘贴板中未发现图片");
            return;
        }
        BufferedImage image = (BufferedImage) imageFromClipboard;
        BdmpOutUtils.convertBinPicToSource(image, saveDirPath);
    }

}
