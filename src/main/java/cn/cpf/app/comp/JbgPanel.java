package cn.cpf.app.comp;

import com.github.cosycode.common.util.io.FileSystemUtils;
import lombok.Getter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <b>Description : </b> 背景面板
 * <p>
 * <b>created in </b> 2021/3/25
 *
 * @author CPF
 * @since 1.0
 **/
public class JbgPanel extends JPanel {

    private JScrollPane sp;

    @Getter
    private JComponent component;

    @Getter
    private Image image;

    @Getter
    private boolean scroll;

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        super.paintComponents(g);
    }

    /**
     * @param scroll false: 若有背景图片, 则背景图片无法滚动(定位在画面上), true: 若有背景图片, 则背景图片可以滚动
     */
    public JbgPanel(Image backgroundImg, JComponent component, boolean scroll) {
        setLayout(new BorderLayout());
        setImage(backgroundImg);
        this.component = component;
        this.scroll = scroll;
        setOpaque(false);
        component.setOpaque(false);
        if (component instanceof JScrollPane) {
            sp = (JScrollPane) component;
            sp.getViewport().setOpaque(false);
            add(component);
        } else if (scroll) {
            sp = new JScrollPane(component);
            // 分别设置水平和垂直滚动条自动出现
            sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            sp.setOpaque(false);
            sp.getViewport().setOpaque(false);
            add(sp);
        } else {
            sp = null;
            add(component);
        }
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public static void main(String args[]) throws IOException {
        JFrame frame = new JFrame();
        final BufferedImage image = ImageIO.read(FileSystemUtils.findFile("classpath:log-area.jpg"));
        final JbgPanel jbgPanel = new JbgPanel(image, new JTextArea(), true);
        frame.setContentPane(jbgPanel);
        frame.setVisible(true);
        frame.setTitle("EndPoint IO Transfer");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(400, 100, 800, 600);
    }

}
