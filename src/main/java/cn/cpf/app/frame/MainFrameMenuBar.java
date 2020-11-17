package cn.cpf.app.frame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainFrameMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	public MainFrameMenuBar() {
		addSettingMenu();
	}

	private void addSettingMenu() {
		JMenu muSetting = new JMenu("clear Log");
		add(muSetting);

		muSetting.addMouseListener((LambdaMouseListener) (e) -> {
			MainFramePanel.getJTextPane().setText("");
		});
	}


	@FunctionalInterface
	public interface LambdaMouseListener extends MouseListener {

		@Override
		default void mouseClicked(MouseEvent e) {
		}

		@Override
		default void mouseReleased(MouseEvent e) {
		}

		@Override
		default void mouseEntered(MouseEvent e) {
		}

		@Override
		default void mouseExited(MouseEvent e) {
		}
	}

}
