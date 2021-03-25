package cn.cpf.util;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.FileFilter;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * @date 2020/11/13 11:50
 **/
public class Utils {

    /**
     * 文件夹递归处理
     * 如果 file 不能通过 fileFilter 过滤条件, 则直接返回.
     * 如果 file 是一个文件, 则执行 fileDisposer 处理方法.
     * 如果 file 是一个文件夹, 则对文件夹中的每个子文件夹和文件递归调用本方法.
     *
     * @param file         文件 或 文件夹.
     * @param fileDisposer 文件处理方式.
     * @param fileFilter   文件过滤器.
     */
    public static void fileDisposeFromDir(File file, FileDisposer fileDisposer, FileFilter fileFilter) {
        if (file == null || !file.exists()) {
            return;
        }
        // 过滤
        if (fileFilter != null && !fileFilter.accept(file)) {
            return;
        }
        if (file.isFile()) {
            fileDisposer.dispose(file);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File item : files) {
                    fileDisposeFromDir(item, fileDisposer, fileFilter);
                }
            }
        } else {
            BinPicUtils.logInfo("非文件或文件夹, 跳过处理! filePath: {}", file.getPath());
        }
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
