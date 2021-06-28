package cn.cpf.app.frame;

import cn.cpf.app.global.CompContext;
import com.github.cosycode.bdmp.BdmpHandle;
import com.github.cosycode.ext.swing.comp.JPathTextField;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/8/11 12:14
 */
@Slf4j
public class PixelPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    @Getter
    private final JPanel boardPanel;

    @Getter
    private final JButton btnOpen;

    @Getter
    private final JButton btnConvert;

    @Getter
    private JTextField tfPath;

    /**
     * Create the panel.
     */
    public PixelPanel() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));

        // 透明
        this.setBackground(null);
        this.setOpaque(false);

        btnOpen = CompContext.registerComponent("SelectFile", new JButton());
        btnOpen.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jfc.showOpenDialog(btnOpen);
            File f = jfc.getSelectedFile();
            if (f != null) {
                String path = f.toString();
                tfPath.setText(path);
            }
        });

        btnConvert = CompContext.registerComponent("convertToBdmp", new JButton());
        btnConvert.addActionListener(e -> {
            String pathText = tfPath.getText();
            if (StringUtils.isBlank(pathText)) {
                return;
            }
            File file = new File(pathText);
            if (!file.exists()) {
                log.warn("文件不存在, 请检查路径是否正确");
                return;
            }
            try {
                String savePath = file.getParentFile().getPath() + File.separator + "outfile" + File.separator;
                final boolean b = BdmpHandle.convertBdmpToFile(pathText, savePath);
                if (b) {
                    log.info("转换成功, 生成源");
                    File outDir = new File(savePath);
                    if (outDir.exists()) {
                        Desktop.getDesktop().open(outDir);
                    }
                } else {
                    log.info("未发现数据");
                }
            } catch (Exception ex) {
                log.error("转换失败", ex);
            }
        });


        tfPath = new JPathTextField();

        boardPanel = new JPanel();
        boardPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        boardPanel.setLayout(new BorderLayout(0, 0));
        boardPanel.setBackground(null);
        boardPanel.setOpaque(false);
        boardPanel.add(btnConvert, BorderLayout.EAST);
        boardPanel.add(btnOpen, BorderLayout.WEST);
        boardPanel.add(tfPath, BorderLayout.CENTER);
        add(boardPanel, BorderLayout.NORTH);
    }

}
