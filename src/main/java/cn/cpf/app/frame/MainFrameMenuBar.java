package cn.cpf.app.frame;

import com.github.cosycode.ext.swing.event.MouseReleasedListener;

import javax.swing.*;

/**
 * <b>Description : </b> 
 *
 * @author CPF
 * @date 2021/2/22
 **/
public class MainFrameMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	public MainFrameMenuBar() {
		addSettingMenu();
	}

	private void addSettingMenu() {
		JMenu muSetting = new JMenu("clear Log");
		add(muSetting);
		JMenu muSetting1 = new JMenu("最下 Log");
		add(muSetting1);
		JMenu muSetting2 = new JMenu("最上 Log");
		add(muSetting2);

		muSetting.addMouseListener((MouseReleasedListener) (e) -> {
			MainFramePanel.getJTextPane().setText("");
		});
		muSetting1.addMouseListener((MouseReleasedListener) (e) -> {
			final JTextPane jTextPane = MainFramePanel.getJTextPane();
			jTextPane.setCaretPosition(jTextPane.getStyledDocument().getLength());
			jTextPane.setAutoscrolls(true);
		});
		muSetting2.addMouseListener((MouseReleasedListener) (e) -> {
			final JTextPane jTextPane = MainFramePanel.getJTextPane();
			jTextPane.setCaretPosition(0);
		});
	}

}
