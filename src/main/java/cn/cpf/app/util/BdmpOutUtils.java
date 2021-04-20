package cn.cpf.app.util;

import com.github.cosycode.bdmp.BdmpHeader;
import com.github.cosycode.bdmp.BdmpRecInfo;
import com.github.cosycode.bdmp.BdmpRecognizer;
import com.github.cosycode.bdmp.BdmpSource;
import com.github.cosycode.common.ext.bean.DoubleBean;
import com.github.cosycode.common.util.io.FileSystemUtils;
import sun.awt.datatransfer.DataTransferer;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * <b>Description : </b> 图片输出工具类
 * <p>
 * <b>created in </b> 2020/11/9
 *
 * @author CPF
 * @since 1.0
 **/
public class BdmpOutUtils {

    private BdmpOutUtils(){}

    /**
     * 将路径指向的 binPic 转换为文件并存储到指定文件夹
     *
     * @param image binPic 图片
     * @param saveDirPath 解析后的文件存储路径
     * @return 是否成功
     */
    public static boolean convertBinPicToSource(BufferedImage image, String saveDirPath) throws IOException {
        final DoubleBean<Boolean, BdmpHeader> headerDoubleBean = convertBinPicToType(image, null, saveDirPath, true);
        return headerDoubleBean != null && headerDoubleBean.getO1();
    }

    /**
     * 将路径指向的 binPic 转换为文件并存储到指定文件夹
     *
     * @param image binPic 图片
     * @param saveDirPath 解析后的文件存储路径
     * @return 是否成功
     */
    public static boolean convertBinPicToFile(BufferedImage image, String saveDirPath, boolean isOverWrite) throws IOException {
        final DoubleBean<Boolean, BdmpHeader> headerDoubleBean = convertBinPicToType(image, BdmpSource.SourceType.TYPE_FILE, saveDirPath, isOverWrite);
        return headerDoubleBean != null && headerDoubleBean.getO1();
    }

    /**
     * 如果解析出来的图片是粘贴板, 则将内容添加到系统粘贴板种
     * 如果解析出来的内容指向文件, 则将内容存储为文件, 存到指定路径, 如果该路径下已存在同名文件, 则根据 isOverWrite 标记做处理,
     * 如果 isOverWrite 为 ture, 则覆盖, 否则跳过图片解析.
     *
     * @param image binPic 图片
     * @param sourceType  转换的内容解析输出方式
     * @param saveDirPath 如果转换的内容是文件, 则解析后的文件存储路径
     * @param isOverWrite 如果解析后的内容是文件, 且文件已存在, 则是否覆盖
     * @return &lt;是否写入成功, 正确转换则返回转换的信息实体&gt;，否则返回null
     * @throws IOException
     */
    public static DoubleBean<Boolean, BdmpHeader> convertBinPicToType(BufferedImage image, BdmpSource.SourceType sourceType, String saveDirPath, boolean isOverWrite) throws IOException {
        // 确保存储的文件夹存在
        final BdmpRecInfo picRecInfo = BdmpRecognizer.resolver(image);
        if (picRecInfo == null) {
            LogUtils.printWarning("未发现像素图片");
            return null;
        }
        LogUtils.printDebug("picRecInfo ==> {}" + picRecInfo);
        boolean check = picRecInfo.check();
        if (!check) {
            LogUtils.printWarning("转换文件失败, MD5值不一样");
            return null;
        }
        final BdmpHeader bdmpHeader = picRecInfo.getBdmpHeader();
        final BdmpSource.SourceType type = Optional.ofNullable(sourceType).orElse(BdmpSource.SourceType.valueOf(bdmpHeader.getType()));
        switch (type) {
            case TYPE_FILE:
                final boolean b = writeFile(saveDirPath, bdmpHeader.getTag(), picRecInfo.getFileContent(), isOverWrite);
                return DoubleBean.of(b, bdmpHeader);
            case TYPE_CONTENT:
                return DoubleBean.of(false, bdmpHeader);
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
        return DoubleBean.of(false, bdmpHeader);
    }

    /**
     * @param saveDirPath 写入文件夹
     * @param fileName 文件名称
     * @param content 写入内容
     * @param isOverWrite 如果文件已存在, 是否覆盖, 若为 false, 则直接跳出
     * @return 是否正确写入了文件
     *
     * @throws IOException
     */
    public static boolean writeFile(String saveDirPath, String fileName, byte[] content, boolean isOverWrite) throws IOException {
        if (!(saveDirPath.endsWith("\\") && saveDirPath.endsWith("/"))) {
            saveDirPath += File.separator;
        }
        LogUtils.printSuccess("转换文件成功! ==> {}", fileName);
        FileSystemUtils.insureFileDirExist(new File(saveDirPath));
        // 写入文件
        LogUtils.printDebug("准备写入文件");
        final String savePath = saveDirPath + fileName;
        final File file = new File(savePath);
        if (!isOverWrite && file.exists()) {
            LogUtils.printWarning("文件已存在 ==> {}", savePath);
            return false;
        }
        try (FileOutputStream outputStream = new FileOutputStream(file)){
            outputStream.write(content);
        }
        LogUtils.printSuccess("写入文件成功 ==> {}", savePath);
        return true;
    }


}
