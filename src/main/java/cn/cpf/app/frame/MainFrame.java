package cn.cpf.app.frame;

import cn.cpf.app.util.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/8/11 11:29
 */
@Slf4j
public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        LogUtils.setFlag(1);
        PluginManager.addPackage("cn.cpf.app.comp.JTextAreaAppender");
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
        try {
            Class.forName("cn.cpf.app.comp.JTextAreaAppender");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        setTitle("ext-tool");
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
