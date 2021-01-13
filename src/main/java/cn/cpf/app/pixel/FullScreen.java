package cn.cpf.app.pixel;

import cn.cpf.app.event.KeyReleasedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class FullScreen {

    public static void main(String[] args) {
        new FullScreenDemo2().main(args);
    }

    static class FullScreenDemo1 {
        public void main(String[] args) {
            final JFrame jframe = new JFrame();
            JButton fullsButton = new JButton("全屏显示");
            JButton exitButton = new JButton("退出");
            exitButton.addActionListener(evt -> System.exit(1));
            fullsButton.addActionListener(evt -> {
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                //通过调用GraphicsEnvironment的getDefaultScreenDevice方法获得当前的屏幕设备了
                GraphicsDevice gd = ge.getDefaultScreenDevice();
                // 全屏设置
                gd.setFullScreenWindow(jframe);
            });
            jframe.add(fullsButton);
            jframe.add(exitButton);
            jframe.setLayout(new FlowLayout());
            jframe.setSize(400, 300);
            jframe.setVisible(true);
        }
    }

    static class FullScreenDemo2 {

        public void main(String[] args) {
            JFrame jframe = new JFrame();
            JButton exitButton = new JButton("退出");
            exitButton.addActionListener(evt -> System.exit(1));
            jframe.add(exitButton);
            jframe.setLayout(new FlowLayout());
            jframe.setUndecorated(false);
            jframe.getGraphicsConfiguration().getDevice()
                    .setFullScreenWindow(jframe);
            jframe.setVisible(true);
        }
    }

    static class FullScreenDemo3 {
        public void main(String[] args) {
            JFrame jframe = new JFrame();
            JButton exitButton = new JButton("退出");
            exitButton.addActionListener(evt -> System.exit(1));
            jframe.add(exitButton);
            jframe.setLayout(new FlowLayout());
            /*
             * true无边框 全屏显示
             * false有边框 全屏显示
             */
            jframe.setUndecorated(false);
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            jframe.setSize(d.width, d.height);
            jframe.setVisible(true);
        }
    }

    static class FullScreenDemo4 {
        public void main(String[] args) {
            JFrame jframe = new JFrame();
            JButton exitButton = new JButton("退出");
            exitButton.addActionListener(evt -> System.exit(1));
            jframe.addKeyListener((KeyReleasedListener) e -> {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(1);
                }
            });
            jframe.add(exitButton);
            jframe.setLayout(new FlowLayout());
            /*
             * true无边框 全屏显示
             * false有边框 全屏显示
             */
            jframe.setUndecorated(false);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle bounds = new Rectangle(screenSize);
            jframe.setBounds(bounds);
            jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
            jframe.setVisible(true);
        }
    }

}
