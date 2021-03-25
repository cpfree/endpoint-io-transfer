package cn.cpf.man;

import cn.cpf.bdmp.*;
import cn.cpf.util.CommandLineHelper;
import cn.cpf.util.ImageSlideShowFrame;
import cn.cpf.util.Utils;
import sun.awt.datatransfer.DataTransferer;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <b>Description : </b> 将剪贴板中的文本渲染成 bit-data-map 图片. 并显示
 * <p> command: java -cp remote-tool.jar cn.cpf.man.ShowFrameWithBdmpForClipboard -r 800 -px 2 -m 20
 * <b>created in </b> 2020/11/14
 *
 * @author CPF
 * @since 1.0
 **/
public class ShowFrameWithBdmpForClipboard {

    /**
     * @param args <p>-r : 生成的图片一行渲染多少个数据点
     *             <p>-px: 每个数据点的边长
     *             <p>-m : 生成的图片边缘宽度
     *             <p>-p2: 生成的每个数据点携带几个bit数据(默认为8)
     * @throws IOException 粘贴板读取失败
     */
    @SuppressWarnings("java:S106")
    public static void main(String[] args) throws IOException {
        // 获取参数
        final CommandLineHelper lineArgs = CommandLineHelper.parse(args);
        final int rowPxNum = lineArgs.getDefaultParam("r", 920);
        final int pxSideLen = lineArgs.getDefaultParam("px", 2);
        final int margin = lineArgs.getDefaultParam("m", 20);
        final int powerOf2 = lineArgs.getDefaultParam("p2", 8);
        // 获取粘贴板
        final Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        final Transferable contents = systemClipboard.getContents(null);
        // 生成图像
        final DataFlavor suitableFlavor = Utils.getSuitableFlavor();
        final byte[] bytes = DataTransferer.getInstance().translateTransferable(contents, suitableFlavor, 0);
        final BdmpSource pixelPngSource = BdmpSource.geneByClipboard(suitableFlavor.getMimeType(), bytes);
        BdmpGeneConfig config = new BdmpGeneConfig();
        config.setMargin(margin);
        config.setRowPixelCnt(rowPxNum);
        config.setPixelSideWidth(pxSideLen);
        config.setPixelSideHeight(pxSideLen);
        config.setMappingColor(BdmpUtils.getPxType(powerOf2));
        final BdmpGeneInfo bdmpGeneInfo = new BdmpGeneInfo(config, pixelPngSource);
        final BufferedImage image = PixelPngDrawer.geneRatePixelPng(bdmpGeneInfo);
        // 显示图像
        ImageSlideShowFrame frame = new ImageSlideShowFrame();
        frame.setPixelImage(image);
        frame.suitSize();
    }


}
