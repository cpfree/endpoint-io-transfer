package cn.cpf.app.frame;

import cn.cpf.app.global.ConfigHelper;
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
        String[] tabNames = {"keyIn", "picRecognize", "realtimeScanner"};

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
        JScrollPane jScrollPane = new JScrollPane(jTextPane);

        // 分别设置水平和垂直滚动条自动出现
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel logPane = new JPanel();
        logPane.setLayout(new BorderLayout());
        logPane.add(jScrollPane, BorderLayout.CENTER);

        jScrollPane.setPreferredSize(new Dimension(jScrollPane.getPreferredSize().width, 200));

        add(logPane, BorderLayout.SOUTH);
    }

}
