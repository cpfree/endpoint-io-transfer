package cn.cpf.app.main;

import cn.cpf.app.util.LogUtils;
import com.github.cosycode.bdmp.BdmpHandle;
import com.github.cosycode.common.helper.CommandLineHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 将文件转换为bit-data-map
 */
public class BdmpGeneForFile {

    /**
     * @param args -f 待转换为bit-data-map的源文件路径(不能为空)
     *             <p>-d : 生成的图片保存路径
     *             <p>-r : 生成的图片一行渲染多少个数据点
     *             <p>-px: 每个数据点的边长
     *             <p>-m : 生成的图片边缘宽度
     *             <p>-p2: 生成的每个数据点携带几个bit数据(默认为8)
     *             <p>-inv: 如果路径是文件夹, 文件夹里面有多张图片, 渲染每张图片的时间间隔
     *             <p>-maxLen: 如果路径是文件夹, 渲染的文件最大大小, 默认最大为400K, 如果文件大小超过400K, 则跳过渲染
     * @throws IOException 文件读取异常
     */
    public static void main(String[] args) throws IOException {
        CommandLineHelper.requireNonEmpty(args, "参数不能为空");
        final CommandLineHelper lineArgs = CommandLineHelper.parse(args);
        final String filePath = lineArgs.getParam("f");
        if (filePath == null || filePath.isEmpty()) {
            throw new RuntimeException("文件参数 `-f` 不能为空");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在");
        }
        if (!file.isFile() || !file.canRead()) {
            throw new IOException("file: " + filePath + " 不是文件, 或者文件不可读");
        }
        final String savePath = lineArgs.getDefaultParam("d", file.getParent());
        final int rowPxNum = lineArgs.getDefaultParam("r", 920);
        final int pxWidth = lineArgs.getDefaultParam("px", 2);
        final int margin = lineArgs.getDefaultParam("m", 20);
        final int powerOf2 = lineArgs.getDefaultParam("p2", 8);
        LogUtils.printInfo("file: {}, saveDir: {}", filePath, savePath);
        BdmpHandle.convertFileToBdmp(filePath, savePath, rowPxNum, pxWidth, margin, (byte) powerOf2);
    }

}
