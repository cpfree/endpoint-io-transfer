package cn.cpf.app.frame;

import cn.cpf.app.comp.JbgPanel;
import cn.cpf.app.global.CompContext;
import com.github.cosycode.common.ext.hub.Throws;
import com.github.cosycode.common.util.io.FileSystemUtils;
import com.github.cosycode.ext.swing.event.MouseReleasedListener;
import lombok.Getter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <b>Description : </b>
 * <p>
 * <b>created in </b> 2021/3/30
 *
 * @author CPF
 * @since 1.1
 **/
public class LogPanel extends JPanel {

    @Getter
    private static final JTextPane jTextPane = new JTextPane();

    /**
     * Create the panel.
     */
    public LogPanel() {
        setLayout(new BorderLayout(0, 0));
        // 透明
        this.setBackground(Color.gray);
        this.setOpaque(false);
        jTextPane.setAutoscrolls(true);
        jTextPane.setBackground(Color.gray);
        jTextPane.setEditable(false);

        JToolBar toolBar = renderToolBar();
        add(toolBar, BorderLayout.NORTH);
        final BufferedImage image = Throws.fun(FileSystemUtils.findFile("suitpath:log-area.jpg"), ImageIO::read)
                .logThrowable().value();
        final JbgPanel logPane = new JbgPanel(image, jTextPane, true);
        logPane.setPreferredSize(new Dimension(logPane.getPreferredSize().width, 200));
        add(logPane, BorderLayout.CENTER);
    }


    private JToolBar renderToolBar() {
        final JToolBar toolBar = new JToolBar();
        JButton btnClear = CompContext.registerComponent("clearLog", new JButton());
        JButton btnToUp = CompContext.registerComponent("logToUp", new JButton());
        JButton btnToBottom = CompContext.registerComponent("logToBottom", new JButton());
        btnClear.addMouseListener((MouseReleasedListener) (e) -> {
            jTextPane.setText("");
        });
        btnToBottom.addMouseListener((MouseReleasedListener) (e) -> {
            jTextPane.setCaretPosition(jTextPane.getStyledDocument().getLength());
            jTextPane.setAutoscrolls(true);
        });
        btnToUp.addMouseListener((MouseReleasedListener) (e) -> {
            jTextPane.setCaretPosition(0);
        });
        toolBar.add(btnClear);
        toolBar.add(btnToUp);
        toolBar.add(btnToBottom);
        return toolBar;
    }

}
