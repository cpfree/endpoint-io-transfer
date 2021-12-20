package cn.cpf.man;

import cn.cpf.bdmp.BdmpHandle;
import cn.cpf.util.CommandLineHelper;
import cn.cpf.util.ImageSlideShowFrame;
import cn.cpf.util.Utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * <b>Description : </b> 如果参数路径指向文件, 则将文件转换为 bit-data-map 并显示, 如果参数是文件夹, 则遍历文件夹里面的文件, 并按照配置的时间间隔循环显示
 * <p> 1. 直接幻灯片展示文件夹中图片
 * <p> 2. 将文件夹中文件转换成ImageBuffer后直接显示图片
 * <p> command: java -cp remote-tool.jar cn.cpf.man.ShowFrameWithPixelPngForFile -p ${文件或文件夹路径} -r 800 -px 2 -m 20 -inv 2000 -maxLen 300000
 * <b>created in </b> 2020/11/14
 *
 * @author CPF
 * @since 1.0
 **/
public class ShowFrameWithBdmpForFile {

    /**
     * @param args -p 文件或文件夹路径
     *             <p>-r : 生成的图片一行渲染多少个数据点
     *             <p>-px: 每个数据点的边长
     *             <p>-m : 生成的图片边缘宽度
     *             <p>-p2: 生成的每个数据点携带几个bit数据(默认为8)
     *             <p>-inv: 如果路径是文件夹, 文件夹里面有多张图片, 渲染每张图片的时间间隔
     *             <p>-maxLen: 如果路径是文件夹, 渲染的文件最大大小, 默认最大为400K, 如果文件大小超过400K, 则跳过渲染
     * @throws IOException 文件读取异常
     */
    @SuppressWarnings("java:S106")
    public static void main(String[] args) throws IOException {
        CommandLineHelper.requireNonEmpty(args, "参数不能为空");
        final CommandLineHelper lineArgs = CommandLineHelper.parse(args);
        final String path = lineArgs.getParam("p");
        final int rowPxNum = lineArgs.getDefaultParam("r", 920);
        final int pxWidth = lineArgs.getDefaultParam("px", 2);
        final int margin = lineArgs.getDefaultParam("m", 20);
        final int powerOf2 = lineArgs.getDefaultParam("p2", 8);
        final int interval = lineArgs.getDefaultParam("inv", 2000);
        final int maxLen = lineArgs.getDefaultParam("maxLen", 1024 * 400);
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
                pixelGenerateFrame.setVisible(true);
                if (file.isDirectory()) {
                    Thread.sleep(interval);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }, file2 -> file2.isDirectory() || file2.length() <= maxLen);
    }

}
