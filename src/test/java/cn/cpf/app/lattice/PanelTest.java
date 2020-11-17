package cn.cpf.app.lattice;

import cn.cpf.app.util.OsUtils;
import cn.cpf.app.util.BdmpOutUtils;
import com.github.cpfniliu.common.ext.hub.LazySingleton;
import com.github.cpfniliu.common.thread.AsynchronousProcessor;
import com.github.cpfniliu.common.thread.PauseableThread;
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
public class PanelTest {

    public static final String saveDirPath = "D:\\Users\\CPF\\Desktop\\out\\";

    public static AsynchronousProcessor<BufferedImage> processor = new AsynchronousProcessor<>(PanelTest::test, null, 1, false);

    private static LazySingleton<Robot> robot = LazySingleton.of(() -> {
        try {
            return new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    });

    private static PauseableThread pauseableThread = new PauseableThread(() -> {
        try {
            final BufferedImage mainScreenShot = OsUtils.getMainScreenShot(robot.instance());
            processor.add(mainScreenShot);
            return true;
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return false;
    });


    public static void main(String[] args) throws AWTException {
//        processor.start();
//        pauseableThread.setMillisecond(5000);
//        pauseableThread.start();
        final BufferedImage mainScreenShot = OsUtils.getMainScreenShot(null);
        test(mainScreenShot);
    }

    private static boolean test(BufferedImage image) {
        try {
            BdmpOutUtils.convertBinPicToFile(image, saveDirPath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
