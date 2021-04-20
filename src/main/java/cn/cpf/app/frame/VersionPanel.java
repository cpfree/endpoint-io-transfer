/*
 * Created by JFormDesigner on Tue Apr 20 09:43:14 CST 2021
 */

package cn.cpf.app.frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author Brainrain
 */
public class VersionPanel extends JFrame {
    public VersionPanel() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        panel3 = new JPanel();
        textField1 = new JTextField();
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        textPane1 = new JTextPane();
        panel2 = new JPanel();
        progressBar1 = new JProgressBar();
        panel1 = new JPanel();
        toolBar1 = new JToolBar();
        button1 = new JButton();
        button2 = new JButton();

        //======== this ========
        setTitle("version");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridLayout(3, 0));

                //======== panel3 ========
                {
                    panel3.setLayout(new BorderLayout());
                    panel3.add(textField1, BorderLayout.CENTER);

                    //---- label1 ----
                    label1.setText("log");
                    panel3.add(label1, BorderLayout.WEST);
                }
                contentPanel.add(panel3);

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(textPane1);
                }
                contentPanel.add(scrollPane1);

                //======== panel2 ========
                {
                    panel2.setLayout(new BorderLayout(1, 8));
                    panel2.add(progressBar1, BorderLayout.SOUTH);
                }
                contentPanel.add(panel2);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== panel1 ========
            {
                panel1.setLayout(new CardLayout());

                //======== toolBar1 ========
                {
                    toolBar1.setFloatable(false);
                    toolBar1.setBackground(new Color(102, 102, 102));

                    //---- button1 ----
                    button1.setText("\u68c0\u67e5\u66f4\u65b0");
                    toolBar1.add(button1);

                    //---- button2 ----
                    button2.setText("\u5347\u7ea7");
                    toolBar1.add(button2);
                }
                panel1.add(toolBar1, "card1");
            }
            dialogPane.add(panel1, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel panel3;
    private JTextField textField1;
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JTextPane textPane1;
    private JPanel panel2;
    private JProgressBar progressBar1;
    private JPanel panel1;
    private JToolBar toolBar1;
    private JButton button1;
    private JButton button2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
