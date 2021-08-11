package cn.cpf.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * <b>Description : </b>
 * <p>
 * <b>created in </b> 2020/11/6
 *
 * @author CPF
 * @since 1.0
 **/
public class ImageSlideShowFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLabel label;
    private JScrollPane jScrollPane;
    private BufferedImage bufferedImage;

    public ImageSlideShowFrame() {
        setTitle("PixelShow");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(1);
                }
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
            add(jScrollPane);
        }
        label.setIcon(new ImageIcon(image));
    }

    public static void main(String[] args) throws IOException {
        final BufferedImage image = ImageIO.read(new File("D:\\Users\\CPF\\Pictures\\信息\\英语音标.JPG"));
        // 显示图像
        ImageSlideShowFrame frame = new ImageSlideShowFrame();
        frame.setPixelImage(image);
        frame.suitSize();
//        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

}
