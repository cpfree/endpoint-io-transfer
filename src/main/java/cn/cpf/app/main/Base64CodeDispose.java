package cn.cpf.app.main;

import cn.cpf.app.util.LogUtils;
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
public class Base64CodeDispose {

    public static void main(String[] args) throws IOException {
        if (args == null || args.length < 1) {
            throw new RuntimeException("参数不能为空");
        }
        LogUtils.printInfo(Arrays.toString(args));
        final String path = args[0];
        String savePath;
        if (args.length >= 2) {
            savePath = args[1];
        }  else {
            savePath = path + ".base64";
        }
        final String s = DataConvertUtils.fileToBase64(new File(path));
        IoUtils.writeFile(savePath, s.getBytes());
    }

}
