package cn.cpf.app.frame;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/8/11 12:13
 */
public class MainFramePanel extends JPanel {

    @Getter
    private static final JTextPane jTextPane = new JTextPane();
    @Getter
    private final JSplitPane splitPane;
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

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(tabbedPane);
        splitPane.setBottomComponent(new LogPanel());
        splitPane.setFocusable(true);
        splitPane.setAutoscrolls(true);
        splitPane.setIgnoreRepaint(false);

        add(splitPane, BorderLayout.CENTER);
    }

}
