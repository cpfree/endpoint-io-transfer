package cn.cpf.app.main;

import cn.cpf.app.util.BdmpOutUtils;
import cn.cpf.app.util.OsUtils;
import com.github.cpfniliu.common.ext.hub.LazySingleton;
import com.github.cpfniliu.common.ext.hub.SimpleCode;
import com.github.cpfniliu.common.thread.AsynchronousProcessor;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * @date 2020/11/3 15:16
 **/
@Slf4j
public class RealTimeScanScreenShot {

    public static final String saveDirPath = "D:\\Users\\CPF\\Desktop\\out\\realtime\\";

    public static AsynchronousProcessor<BufferedImage> processor;

    private static LazySingleton<Robot> robotSinTon;

    static {
        robotSinTon = LazySingleton.of(() -> SimpleCode.runtimeException(() -> new Robot(), "创建Robot对象失败"));
        processor = AsynchronousProcessor.ofPredicate(RealTimeScanScreenShot::test);
        processor.start();
    }

    public static void main(String[] args) throws AWTException {
        final BufferedImage mainScreenShot = OsUtils.getMainScreenShot(null);
        test(mainScreenShot);
    }

    private static boolean test(BufferedImage image) {
        try {
            BdmpOutUtils.convertBinPicToFile(image, saveDirPath, false);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
