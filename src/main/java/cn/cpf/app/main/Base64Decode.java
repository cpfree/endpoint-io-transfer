package cn.cpf.app.main;

import cn.cpf.app.util.LogUtils;
import com.github.cosycode.common.helper.CommandLineHelper;

import java.io.*;
import java.nio.file.FileSystemException;
import java.util.Base64;

/**
 * <b>Description : </b> 命令行入口, 解析64编码为文件
 *
 * @author CPF
 * @date 2020/11/17
 **/
public class Base64Decode {

    public static void main(String[] args) throws IOException {
        CommandLineHelper.requireNonEmpty(args, "参数不能为空");
        final CommandLineHelper lineArgs = CommandLineHelper.parse(args);
        final String filePath = lineArgs.getParam("fp");
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在 ==> " + filePath);
        }
        if (!file.isFile()) {
            throw new FileSystemException("不是文件 ==> " + filePath);
        }
        final String savePath = lineArgs.getDefaultParam("sp", file.getParentFile().getPath() + File.separator + "base64.zip");
        try(FileReader reader = new FileReader(file)) {
            char[] chars = new char[(int) file.length()];
            final int read = reader.read(chars);
            LogUtils.printDebug("读取文件长度: {}", read);
            String base64 = new String(chars);
            byte[] bytes = Base64.getDecoder().decode(base64);
            writeFile(savePath, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String savePath, byte[] content) {
        final File file = new File(savePath);
        try (final FileOutputStream writer = new FileOutputStream(file)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
