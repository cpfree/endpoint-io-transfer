package cn.cpf.app.frame;

import cn.cpf.app.global.CompContext;
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
        String[] tabNames = {"TextPrinting", "PictureRecognition", "MainScreenScanning"};

        keyInPane = new KeyInputPanel();
        keyInPane.setBackground(null);
        keyInPane.setOpaque(false);

        pixelPane = new PixelPanel();
        pixelPane.setBackground(null);
        pixelPane.setOpaque(false);

        runtimeScanPane = new RuntimeScanPanel();
        runtimeScanPane.setBackground(null);
        runtimeScanPane.setOpaque(false);

        CompContext.register(new CompContext.CompBean<JComponent>() {
            @Override
            public void check() {
                tabbedPane.removeAll();
                tabbedPane.addTab(getLangString(tabNames[0]), keyInPane);
                tabbedPane.addTab(getLangString(tabNames[1]), pixelPane);
                tabbedPane.addTab(getLangString(tabNames[2]), runtimeScanPane);
            }
        });

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(tabbedPane);
        splitPane.setBottomComponent(new LogPanel());
        splitPane.setFocusable(true);
        splitPane.setAutoscrolls(true);
        splitPane.setIgnoreRepaint(false);

        add(splitPane, BorderLayout.CENTER);
    }

}
