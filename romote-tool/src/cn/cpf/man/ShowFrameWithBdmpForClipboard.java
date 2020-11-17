package cn.cpf.man;

import cn.cpf.bdmp.*;
import cn.cpf.util.CommandLineArgs;
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
 * <b>Description : </b>
 *
 * @author CPF
 * @date 2020/11/14 2:06
 **/
public class ShowFrameWithBdmpForClipboard {

    @SuppressWarnings("java:S106")
    public static void main(String[] args) throws IOException {
        // 获取参数
        final CommandLineArgs lineArgs = CommandLineArgs.analyze(args);
        final int rowPxNum = lineArgs.getDefaultKeyVal("r", 920);
        final int pxSideLen = lineArgs.getDefaultKeyVal("px", 2);
        final int margin = lineArgs.getDefaultKeyVal("m", 20);
        final int powerOf2 = lineArgs.getDefaultKeyVal("p2", 8);
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
