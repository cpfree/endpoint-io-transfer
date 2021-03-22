package cn.cpf.app.frame;


import com.github.cosycode.ext.swing.event.KeyReleasedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * @date 2020/11/6 17:58
 **/
public class ImageSlideShowFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLabel label;
    private JScrollPane jScrollPane;
    private BufferedImage bufferedImage;

    public ImageSlideShowFrame() {
        setTitle("PixelShow");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addKeyListener((KeyReleasedListener) e -> {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(1);
            }
        });
    }

    /**
     * true无边框 全屏显示
     * false有边框 全屏显示
     */
    public void setFrameSize(Dimension screenSize) {
        Rectangle bounds = new Rectangle(screenSize);
        setBounds(bounds);
        setSize(screenSize);
        setVisible(true);
    }

    /**
     * true无边框 全屏显示
     * false有边框 全屏显示
     */
    public void suitSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (bufferedImage != null) {
            screenSize.setSize(Math.min(bufferedImage.getWidth() + 20, screenSize.width), Math.min(screenSize.height, bufferedImage.getHeight() + 44));
        }
        setFrameSize(screenSize);
    }

    /**
     * @param image
     */
    public void setPixelImage(BufferedImage image) {
        Objects.requireNonNull(image);
        this.bufferedImage = image;
        if (label == null) {
            label = new JLabel();
            jScrollPane = new JScrollPane(label);
            // 分别设置水平和垂直滚动条自动出现
            jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            jScrollPane.getVerticalScrollBar().setUnitIncrement(100);
            jScrollPane.getVerticalScrollBar().setBlockIncrement(100);
            add(jScrollPane);
        }
        label.setIcon(new ImageIcon(image));
    }


}
