package cn.cpf.app.frame;

import cn.cpf.app.comp.JTextAreaAppender;
import cn.cpf.app.global.CompContext;
import cn.cpf.app.util.LogUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;
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

    private final MainFramePanel contentPane;

    static {
        // 代码设置log4j2
        LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
        final Configuration configuration = loggerContext.getConfiguration();
        final PatternLayout layout = PatternLayout.newBuilder().withPattern("[%-5p] %d{HH:mm:ss} - %m%n").build();
        Appender appender = new JTextAreaAppender("TextArea", null, layout, false, LogPanel.getJTextPane());
        appender.start();
        configuration.addAppender(appender);
        configuration.getRootLogger().addAppender(appender, Level.DEBUG, null);
        // 设置打印标记
        LogUtils.setLogFlag(true);
        log = LoggerFactory.getLogger(MainFrame.class);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new MainFrame();
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
        setBounds(400, 100, 800, 400);
        // 添加菜单
        MainFrameMenuBar menuBar = new MainFrameMenuBar();
        menuBar.setVisible(true);
        setJMenuBar(menuBar);
        // 添加面板
        contentPane = new MainFramePanel();
        contentPane.setPreferredSize(new Dimension(700, 600));
        contentPane.setVisible(true);
        setContentPane(contentPane);
        CompContext.changeLang("cn");
        pack();
        contentPane.getSplitPane().setResizeWeight(0.5);
        contentPane.getSplitPane().setDividerLocation(0.7);
        // 窗口变化事件
        addWindowStateListener(e -> {
            final int newState = e.getNewState();
            if (newState != e.getOldState()) {
                switch (newState) {
                    case Frame.MAXIMIZED_BOTH:
                        log.debug("最大化");
                        break;
                    case Frame.NORMAL:
                        log.debug("恢复");
                        break;
                    case Frame.ICONIFIED:
                    case Frame.ICONIFIED | Frame.MAXIMIZED_BOTH:
                        log.debug("最小化");
                        break;
                    default:
                        log.debug("window other : {}", newState);
                        break;
                }
            }
        });

    }


}
