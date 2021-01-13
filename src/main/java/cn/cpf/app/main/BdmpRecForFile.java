package cn.cpf.app.main;

import cn.cpf.app.util.BdmpOutUtils;
import cn.cpf.app.util.OsUtils;
import com.github.cosycode.bdmp.BdmpUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 像素点阵图片解析器(用于命令行调用)
 */
public class BdmpRecForFile {

    public static void main(String[] args) throws IOException {
        if (args == null || args.length < 1) {
            throw new RuntimeException("参数不能为空");
        }
        final String path = args[0];
        String savePath;
        if (args.length >= 2) {
            savePath = args[1];
        }  else {
            savePath = OsUtils.getFileDir(path) + "out" + File.separator;
        }
        BufferedImage image = BdmpUtils.load(path);
        BdmpOutUtils.convertBinPicToSource(image, savePath);
    }

}
