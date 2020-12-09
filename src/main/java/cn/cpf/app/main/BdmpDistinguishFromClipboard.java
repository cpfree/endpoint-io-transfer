package cn.cpf.app.main;

import cn.cpf.app.util.LogUtils;
import cn.cpf.app.util.OsUtils;
import cn.cpf.app.util.BdmpOutUtils;
import com.github.cpfniliu.common.helper.CommandLineHelper;

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

    public static final String SAVE_DIR_PATH = "E:\\res\\FMBS_DEV_BANK\\inner\\clipout\\";

    public static void main(String[] args) throws IOException, UnsupportedFlavorException {
        CommandLineHelper commandLineHelper = CommandLineHelper.parse(args);
        final String saveDirPath = commandLineHelper.getDefaultParam("p", SAVE_DIR_PATH);
        final Image imageFromClipboard = OsUtils.getImageFromClipboard();
        if (imageFromClipboard == null) {
            LogUtils.printWarning("将粘贴板中未发现图片");
            return;
        }
        BufferedImage image = (BufferedImage) imageFromClipboard;
        BdmpOutUtils.convertBinPicToSource(image, saveDirPath);
    }

}
