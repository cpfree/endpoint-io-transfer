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

		muSetting.addMouseListener((MouseReleasedListener) (e) -> {
			MainFramePanel.getJTextPane().setText("");
		});
	}

}
