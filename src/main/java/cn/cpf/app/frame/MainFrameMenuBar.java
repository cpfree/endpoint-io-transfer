package cn.cpf.app.frame;

import cn.cpf.app.event.MouseReleasedListener;

import javax.swing.*;

public class MainFrameMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	public MainFrameMenuBar() {
		addSettingMenu();
	}

	private void addSettingMenu() {
		JMenu muSetting = new JMenu("clear Log");
		add(muSetting);

		muSetting.addMouseListener((MouseReleasedListener) (e) -> {
			MainFramePanel.getJTextPane().setText("");
		});
	}

}
