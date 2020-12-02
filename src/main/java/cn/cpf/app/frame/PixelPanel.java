package cn.cpf.app.frame;

import cn.cpf.app.comp.JPathTextField;
import cn.cpf.app.comp.PauseableThread;
import cn.cpf.app.util.BdmpOutUtils;
import cn.cpf.app.util.OsUtils;
import com.github.cpfniliu.bdmp.BdmpHandle;
import com.github.cpfniliu.common.ext.hub.LazySingleton;
import com.github.cpfniliu.common.ext.hub.SimpleCode;
import com.github.cpfniliu.common.thread.AsynchronousProcessor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/8/11 12:14
 */
@Slf4j
public class PixelPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    @Getter
    private JPanel boardPanel;

    @Getter
    private JButton btnOpen;

    @Getter
    private JButton btnConvert;

    @Getter
    private JTextField tfPath;

    /**
     * Create the panel.
     */
    public PixelPanel() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));

        // 透明
        this.setBackground(null);
        this.setOpaque(false);

        btnOpen = new JButton("OPEN");
        btnOpen.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jfc.showOpenDialog(btnOpen);
            File f = jfc.getSelectedFile();
            if (f != null) {
                String path = f.toString();
                tfPath.setText(path);
            }
        });

        btnConvert = new JButton("转换");
        btnConvert.addActionListener(e -> {
            log.debug("btnConvert click");
            String pathText = tfPath.getText();
            if (StringUtils.isBlank(pathText)) {
                return;
            }
            File file = new File(pathText);
            if (!file.exists()) {
                log.warn("文件不存在, 请检查路径是否正确");
                return;
            }
            try {
                String savePath = file.getParentFile().getPath() + File.separator + "outfile" + File.separator;
                BdmpHandle.convertBdmpToFile(pathText, savePath);
                log.info("转换成功");
                File outDir = new File(savePath);
                if (outDir.exists()) {
                    Desktop.getDesktop().open(outDir);
                }
            } catch (Exception ex) {
                log.error("转换失败", ex);
            }
        });


        tfPath = new JPathTextField();

        boardPanel = new JPanel();
        boardPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        boardPanel.setLayout(new BorderLayout(0, 0));
        boardPanel.setBackground(null);
        boardPanel.setOpaque(false);
        boardPanel.add(btnConvert, BorderLayout.EAST);
        boardPanel.add(btnOpen, BorderLayout.WEST);
        boardPanel.add(tfPath, BorderLayout.CENTER);
        add(boardPanel, BorderLayout.NORTH);

        final JPanel scanPanel = getScanPanel();
        add(scanPanel, BorderLayout.SOUTH);
    }

    private static PauseableThread pauseableThread;

    public static final String saveDirPath = "D:\\Users\\CPF\\Desktop\\out\\realtime\\";

    public static AsynchronousProcessor<BufferedImage> processor;

    private static LazySingleton<Robot> robotSinTon;

    private static boolean test(BufferedImage image) {
        try {
            BdmpOutUtils.convertBinPicToFile(image, saveDirPath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private JPanel getScanPanel() {
        robotSinTon = LazySingleton.of(() -> SimpleCode.runtimeException(() -> new Robot(), "创建Robot对象失败"));
        processor = new AsynchronousProcessor<>(PixelPanel::test);
        processor.start();
        pauseableThread = PauseableThread.builder().booleanSupplier(() -> {
            try {
                final BufferedImage mainScreenShot = OsUtils.getMainScreenShot(robotSinTon.instance());
                processor.add(mainScreenShot);
                return true;
            } catch (AWTException e) {
                e.printStackTrace();
            }
            return false;
        }).millisecond(2000).build();

        JButton startButton = new JButton("开始扫描");
        startButton.addActionListener((e) -> {
            pauseableThread.start();
            log.info("startButton");
        });
        JButton scanButton = new JButton("wake扫描");
        scanButton.addActionListener((e) -> {
            pauseableThread.wake();
            log.info("wake");
        });
        JButton pauseScanBtn = new JButton("暂停扫描");
        pauseScanBtn.addActionListener((e) -> {
            pauseableThread.pause();
            log.info("pause");
        });
        JPanel jPanel = new JPanel();
        jPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        jPanel.setLayout(new BorderLayout(0, 0));
        jPanel.setBackground(null);
        jPanel.setOpaque(false);
        jPanel.add(startButton, BorderLayout.WEST);
        jPanel.add(scanButton, BorderLayout.CENTER);
        jPanel.add(pauseScanBtn, BorderLayout.EAST);
        return jPanel;
    }

}
