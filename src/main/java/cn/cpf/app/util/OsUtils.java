package cn.cpf.app.util;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * @date 2020/11/9 11:37
 **/
public class OsUtils {

    private OsUtils(){}

    /**
     * 从剪切板获得图片。
     */
    public static Image getImageFromClipboard() throws IOException, UnsupportedFlavorException {
        Clipboard sac = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable cc = sac.getContents(null);
        if (cc == null)
            return null;
        else if (cc.isDataFlavorSupported(DataFlavor.imageFlavor))
            return (Image) cc.getTransferData(DataFlavor.imageFlavor);
        return null;
    }

    /**
     * @return 获取主屏幕截图
     */
    public static BufferedImage getMainScreenShot(Robot robot) throws AWTException {
        if (robot == null) {
            robot = new Robot();
        }
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        return robot.createScreenCapture(screenRect);
    }

    /**
     * @return 获取主屏幕截图
     */
    public static BufferedImage getMainScreenShot() throws AWTException {
        return getMainScreenShot(null);
    }

    /**
     * 将路径指向的 Bdmp 转换为文件并存储到 Bdmp 路径下的 outfile 文件夹
     *
     * @param picPath Bdmp 图片路径
     */
    public static String getFileDir(String picPath) {
        return new File(picPath).getParentFile().getPath() + File.separator;
    }

    public static DataFlavor getSuitableFlavor() {
        final Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        if (systemClipboard.isDataFlavorAvailable(DataFlavor.allHtmlFlavor)) {
            return DataFlavor.allHtmlFlavor;
        } else if (systemClipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
            return DataFlavor.stringFlavor;
        } else if (systemClipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
            return DataFlavor.imageFlavor;
        }
        throw new UnsupportedOperationException("不支持的类型");
    }

    public static DataFlavor getSuitableFlavor(String dataFlavorString) {
        if (DataFlavor.allHtmlFlavor.getMimeType().equals(dataFlavorString)) {
            return DataFlavor.allHtmlFlavor;
        } else if (DataFlavor.stringFlavor.getMimeType().equals(dataFlavorString)) {
            return DataFlavor.stringFlavor;
        } else if (DataFlavor.imageFlavor.getMimeType().equals(dataFlavorString)) {
            return DataFlavor.imageFlavor;
        }
        throw new UnsupportedOperationException("不支持的类型");
    }

}
