package cn.cpf.app.frame;

import cn.cpf.app.global.CompContext;
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
        JMenu muSetting = CompContext.registerComponent("menu.Help", new JMenu());
        add(muSetting);

        JMenu langMenu = CompContext.registerComponent("language", new JMenu());
        JMenuItem langEn = CompContext.registerComponent("lang.en", new JRadioButtonMenuItem());
        langEn.addMouseListener((MouseReleasedListener) (e) -> {
            CompContext.changeLang("en");
        });
        JMenuItem langCn = CompContext.registerComponent("lang.cn", new JRadioButtonMenuItem("lang.cn", true));
        langCn.addMouseListener((MouseReleasedListener) (e) -> {
            CompContext.changeLang("cn");
        });
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(langCn);
        buttonGroup.add(langEn);
        langMenu.add(langCn);
        langMenu.add(langEn);
        muSetting.add(langMenu);

        addMenuItem(muSetting, "help", e -> {
        });
        addMenuItem(muSetting, "checkForUpdate", e -> {
        });
        addMenuItem(muSetting, "about", e -> {
            try {
                VersionPanel frame = VersionPanel.instance();
                frame.setTitle("about");
                frame.setVisible(true);
                frame.setBounds(200, 100, 400, 300);
            } catch (Exception ex) {
                log.error("main error", ex);
            }
        });

    }


    private void addMenuItem(JMenuItem menu, String itemLabel, MouseReleasedListener listener) {
        JMenuItem info = CompContext.registerComponent(itemLabel, new JMenuItem());
        menu.add(info);
        info.addMouseListener((MouseReleasedListener) (e) -> {
            log.debug(info.getText());
            listener.mouseReleased(e);
        });
    }

}
