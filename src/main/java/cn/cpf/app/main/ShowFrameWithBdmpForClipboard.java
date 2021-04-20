package cn.cpf.app.main;

import cn.cpf.app.frame.ImageSlideShowFrame;
import cn.cpf.app.util.OsUtils;
import com.github.cosycode.bdmp.*;
import com.github.cosycode.common.helper.CommandLineHelper;
import sun.awt.datatransfer.DataTransferer;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <b>Description : </b> 将剪贴板中的文本渲染成 bit-data-map 图片
 * <p>
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
        // 获取参数
        final DataFlavor suitableFlavor = OsUtils.getSuitableFlavor();
        final byte[] bytes = DataTransferer.getInstance().translateTransferable(contents, suitableFlavor, 0);
        // 生成图像
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
