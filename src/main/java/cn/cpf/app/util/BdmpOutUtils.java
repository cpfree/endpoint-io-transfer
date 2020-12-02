package cn.cpf.app.util;

import com.github.cpfniliu.bdmp.BdmpHeader;
import com.github.cpfniliu.bdmp.BdmpRecInfo;
import com.github.cpfniliu.bdmp.BdmpRecognizer;
import com.github.cpfniliu.bdmp.BdmpSource;
import com.github.cpfniliu.common.util.io.IoUtils;
import sun.awt.datatransfer.DataTransferer;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * @date 2020/11/9 11:43
 **/
public class BdmpOutUtils {

    private BdmpOutUtils(){}

    /**
     * 将路径指向的 binPic 转换为文件并存储到指定文件夹
     *
     * @param image binPic 图片
     * @param saveDirPath 解析后的文件存储路径
     */
    @SuppressWarnings("java:S106")
    public static boolean convertBinPicToSource(BufferedImage image, String saveDirPath) throws IOException {
        // 确保存储的文件夹存在
        final BdmpRecInfo picRecInfo = BdmpRecognizer.resolver(image);
        if (picRecInfo == null) {
            LogUtils.printWarning("未发现像素图片");
            return false;
        }
        LogUtils.printDebug("picRecInfo ==> {}" + picRecInfo);
        boolean check = picRecInfo.check();
        if (!check) {
            LogUtils.printWarning("转换文件失败, MD5值不一样");
            return false;
        }
        final BdmpHeader bdmpHeader = picRecInfo.getBdmpHeader();
        final String type = bdmpHeader.getType();
        switch (BdmpSource.SourceType.valueOf(type)) {
            case TYPE_FILE:
                return writeFile(saveDirPath, picRecInfo.getBdmpHeader().getTag(), picRecInfo.getFileContent());
            case TYPE_CONTENT:
                return false;
            case TYPE_CLIPBOARD:
                final String tag = bdmpHeader.getTag();
                final DataFlavor suitableFlavor = OsUtils.getSuitableFlavor(tag);
                LogUtils.printInfo("suitableFlavor.getMimeType() ==> {}", suitableFlavor.getMimeType());
                LogUtils.printInfo("suitableFlavor.getHumanPresentableName() ==> {}", suitableFlavor.getHumanPresentableName());
                final Object o = DataTransferer.getInstance().translateBytes(picRecInfo.getFileContent(), DataFlavor.allHtmlFlavor, 0, null);
                if (o instanceof String) {
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection((String) o), null);
                } else {
                    throw new RuntimeException("不支持的 translateBytes 类型: ");
                }
        }
        return true;
    }

    /**
     * 将路径指向的 binPic 转换为文件并存储到指定文件夹
     *
     * @param image binPic 图片
     * @param saveDirPath 解析后的文件存储路径
     */
    @SuppressWarnings("java:S106")
    public static boolean convertBinPicToFile(BufferedImage image, String saveDirPath) throws IOException {
        // 确保存储的文件夹存在
        final BdmpRecInfo picRecInfo = BdmpRecognizer.resolver(image);
        if (picRecInfo == null) {
            LogUtils.printWarning("未发现像素图片");
            return false;
        }
        LogUtils.printInfo("picRecInfo ==> {}" + picRecInfo);
        boolean check = picRecInfo.check();
        if (!check) {
            LogUtils.printWarning("转换文件失败, MD5值不一样");
            return false;
        }
        return writeFile(saveDirPath, picRecInfo.getBdmpHeader().getTag(), picRecInfo.getFileContent());
    }


    /**
     * @param saveDirPath 写入文件夹
     * @param fileName 文件名称
     * @param content 写入内容
     * @return
     * @throws IOException
     */
    public static boolean writeFile(String saveDirPath, String fileName, byte[] content) throws IOException {
        if (!(saveDirPath.endsWith("\\") && saveDirPath.endsWith("/"))) {
            saveDirPath += File.separator;
        }
        LogUtils.printSuccess("转换文件成功! ==> {}", fileName);
        IoUtils.insureFileDirExist(new File(saveDirPath));
        // 写入文件
        LogUtils.printDebug("准备写入文件");
        final String savePath = saveDirPath + fileName;
        try (FileOutputStream outputStream = new FileOutputStream(new File(savePath))){
            outputStream.write(content);
        }
        LogUtils.printSuccess("写入文件成功 ==> {}", savePath);
        return true;
    }


}
