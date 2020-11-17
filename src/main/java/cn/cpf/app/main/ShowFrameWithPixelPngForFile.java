package cn.cpf.app.main;

import cn.cpf.app.frame.ImageSlideShowFrame;
import cn.cpf.app.util.CommandLineArgs;
import com.github.cpfniliu.bdmp.BdmpHandle;
import com.github.cpfniliu.common.util.io.FileSystemUtils;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ShowFrameWithPixelPngForFile extends JFrame {

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
        FileSystemUtils.fileDisposeFromDir(file, file1 -> {
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
