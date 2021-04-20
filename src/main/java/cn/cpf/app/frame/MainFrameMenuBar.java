package cn.cpf.app.frame;

import com.github.cosycode.ext.swing.event.MouseReleasedListener;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

/**
 * <b>Description : </b> 
 *
 * @author CPF
 * @date 2021/2/22
 **/
@Slf4j
public class MainFrameMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	public MainFrameMenuBar() {
		addSettingMenu();
	}

	private void addSettingMenu() {
		JMenu muSetting = new JMenu("Help");
		add(muSetting);

		addMenuItem(muSetting, "help", e -> {});
		addMenuItem(muSetting, "check for updates...", e -> {});
		addMenuItem(muSetting, "about", e -> {});

	}


	private void addMenuItem(JMenu menu, String itemLabel, MouseReleasedListener listener) {
		JMenuItem info = new JMenuItem(itemLabel);
		menu.add(info);
//		info.addMouseListener(listener);
		info.addMouseListener((MouseReleasedListener) (e) -> {
			log.debug(info.getText());
			listener.mouseReleased(e);
		});
	}

}
