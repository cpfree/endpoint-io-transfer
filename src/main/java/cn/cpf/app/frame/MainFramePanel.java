package cn.cpf.app.frame;

import cn.cpf.app.global.ConfigHelper;
import com.github.cosycode.common.util.io.FileSystemUtils;
import lombok.Getter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/8/11 12:13
 */
public class MainFramePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    @Getter
    private static final JTextPane jTextPane = new JTextPane();
    @Getter
    private final JTabbedPane tabbedPane;
    @Getter
    private final JPanel keyInPane;
    @Getter
    private final JPanel runtimeScanPane;
    @Getter
    private final JPanel pixelPane;

    /**
     * Create the panel.
     */
    public MainFramePanel() {
        setLayout(new BorderLayout(0, 0));
        // 透明
        this.setBackground(null);
        this.setOpaque(false);

        // tab 选项卡
        tabbedPane = new JTabbedPane();
        String[] tabNames = {"文本打印", "图片识别", "主截幕扫描"};

        keyInPane = new KeyInputPanel();
        keyInPane.setBackground(null);
        keyInPane.setOpaque(false);
        tabbedPane.addTab(tabNames[0], keyInPane);

        pixelPane = new PixelPanel();
        pixelPane.setBackground(null);
        pixelPane.setOpaque(false);
        tabbedPane.addTab(tabNames[1], pixelPane);

        runtimeScanPane = new RuntimeScanPanel();
        runtimeScanPane.setBackground(null);
        runtimeScanPane.setOpaque(false);
        tabbedPane.addTab(tabNames[2], runtimeScanPane);

        add(tabbedPane, BorderLayout.CENTER);

        jTextPane.setAutoscrolls(true);
        jTextPane.setBackground(ConfigHelper.getDefaultColor());

        try {
            final BufferedImage image = ImageIO.read(FileSystemUtils.newFile("classpath:log-area.jpg"));
            final JbgPanel logPane = new JbgPanel(image, jTextPane, true);
            logPane.setPreferredSize(new Dimension(logPane.getPreferredSize().width, 200));
            add(logPane, BorderLayout.SOUTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
