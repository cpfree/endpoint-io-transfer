package cn.cpf.app.pixel;

import com.github.cosycode.common.util.io.IoUtils;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * @date 2020/11/10
 **/
@Slf4j
public class PaneFrame extends JFrame {

    private JEditorPane newsPane;
    private final String newsURL = "http://www.baidu.com";

    public PaneFrame() {

        try (final InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("info.html")) {
            newsPane = new JEditorPane(new URL(newsURL));
            assert resourceAsStream != null;
            final String string = IoUtils.readStringFromInputStream(resourceAsStream);
//            newsPane.setContentType("text/html");
//            newsPane.setText(string);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Logger logger = Logger.getGlobal();
        logger.info("start process...");
        logger.warning("memory is running out...");
        logger.fine("ignored.");
        logger.severe("process will be terminated...");

        newsPane.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                Desktop browser = Desktop.getDesktop();
                try {
                    logger.info("logger  " + e.getURL().toURI().getPath());
                    log.warn("log  " + e.getURL().toURI().getPath());
                } catch (URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });
        newsPane.setEditable(false);
        add(newsPane);
        setSize(600, 550);
        setVisible(true);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PaneFrame::new);
    }
}