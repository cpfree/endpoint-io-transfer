package cn.cpf.app.frame;

import cn.cpf.app.util.LogUtils;
import com.github.cosycode.ext.swing.comp.JTextAreaAppender;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * <b>Description : </b> 主窗口程序
 *
 * @author CPF
 * Date: 2020/8/11 11:29
 */

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Logger log;

    private JPanel contentPane;

    static {
        // 设置log
        JTextAreaAppender.setDefaultJTextPane(MainFramePanel.getJTextPane());
        PluginManager.addPackage("cn.cpf.app.comp.JTextAreaAppender");
        LogUtils.setLogFlag(true);
        log = LoggerFactory.getLogger(MainFrame.class);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                log.error("main error", e);
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainFrame() {
        setTitle("EndPoint IO Transfer");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(400, 100, 800, 600);
        // 添加菜单
        MainFrameMenuBar menuBar = new MainFrameMenuBar();
        menuBar.setVisible(true);
        setJMenuBar(menuBar);
        // 添加面板
        contentPane = new MainFramePanel();
        contentPane.setVisible(true);
        setContentPane(contentPane);
    }


}
