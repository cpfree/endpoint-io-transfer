package cn.cpf.man;

import cn.cpf.bdmp.BdmpHandle;
import cn.cpf.util.CommandLineArgs;
import cn.cpf.util.ImageSlideShowFrame;
import cn.cpf.util.Utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 幻灯片批量(文件夹) & 单个
 * 1. 直接幻灯片展示文件夹中图片
 * 2. 将文件夹中文件转换成ImageBuffer后直接显示图片
 */
public class ShowFrameWithPixelPngForFile {

    @SuppressWarnings("java:S106")
    public static void main(String[] args) throws IOException {
        CommandLineArgs.requireNonEmpty(args, "参数不能为空");
        final CommandLineArgs lineArgs = CommandLineArgs.analyze(args);
        final String path = lineArgs.getKeyVal("p");
        final int rowPxNum = lineArgs.getDefaultKeyVal("r", 920);
        final int pxWidth = lineArgs.getDefaultKeyVal("px", 2);
        final int margin = lineArgs.getDefaultKeyVal("m", 20);
        final int powerOf2 = lineArgs.getDefaultKeyVal("p2", 8);
        final int interval = lineArgs.getDefaultKeyVal("inv", 2000);
        final int maxLen = lineArgs.getDefaultKeyVal("maxLen", 1024 * 400);
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在");
        }
        final ImageSlideShowFrame pixelGenerateFrame = new ImageSlideShowFrame();
        Utils.fileDisposeFromDir(file, file1 -> {
            try {
                System.out.println(file1.getAbsolutePath());
                final BufferedImage bufferedImage = BdmpHandle.convertFileToBdmp(file1, rowPxNum, pxWidth, margin, (byte) powerOf2);
                pixelGenerateFrame.setPixelImage(bufferedImage);
                pixelGenerateFrame.suitSize();
                if (file.isDirectory()) {
                    Thread.sleep(interval);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }, file2 -> file2.length() < maxLen);
    }

}
