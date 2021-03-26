package cn.cpf.app.main;

import cn.cpf.app.util.LogUtils;
import com.github.cosycode.common.helper.CommandLineHelper;
import com.github.cosycode.common.util.io.IoUtils;
import com.github.cosycode.ext.se.util.DataConvertUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * <b>Description : </b> 命令行入口, 将文件转换为 Base64 位编码
 *
 * @author CPF
 * @date 2020/11/17
 **/
public class Base64EnCode {

    public static void main(String[] args) throws IOException {
        CommandLineHelper.requireNonEmpty(args, "参数不能为空");
        LogUtils.printInfo(Arrays.toString(args));
        final CommandLineHelper lineArgs = CommandLineHelper.parse(args);
        final String filePath = lineArgs.getParam("f");
        final String savePath = lineArgs.getDefaultParam("s", filePath + ".base64");
        final String s = DataConvertUtils.fileToBase64(new File(filePath));
        IoUtils.writeFile(savePath, s.getBytes());
    }

}
