package cn.cpf.app.frame;

import cn.cpf.app.comp.JPathTextField;
import cn.cpf.app.util.BdmpOutUtils;
import cn.cpf.app.util.OsUtils;
import com.github.cpfniliu.common.base.SupplierWithThrow;
import com.github.cpfniliu.common.ext.hub.LazySingleton;
import com.github.cpfniliu.common.ext.hub.SimpleCode;
import com.github.cpfniliu.common.thread.AsynchronousProcessor;
import com.github.cpfniliu.common.thread.CtrlLoopThreadComp;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/8/11 12:14
 */
@Slf4j
public class RuntimeScanPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    @Getter
    private JPanel boardPanel;

    @Getter
    private JButton btnOpen;

    @Getter
    private JButton btnConvert;

    public static final String saveDirPath = "D:\\Users\\CPF\\Desktop\\out\\realtime\\";

    @Getter
    private final JTextField outPath = new JPathTextField(saveDirPath);

    public final transient AsynchronousProcessor<BufferedImage> processor = AsynchronousProcessor.ofPredicate(image -> {
        try {
            String text = outPath.getText();
            if (StringUtils.isBlank(text) || !new File(text).isDirectory()) {
                text = saveDirPath;
                outPath.setText(saveDirPath);
            }
            BdmpOutUtils.convertBinPicToFile(image, text, false);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    });


    private final transient LazySingleton<Robot> robotSinTon = LazySingleton.of(() -> SimpleCode.runtimeException((SupplierWithThrow<Robot, AWTException>) Robot::new, "创建Robot对象失败"));;

    private boolean booleanSupplier() {
        try {
            final BufferedImage mainScreenShot = OsUtils.getMainScreenShot(robotSinTon.instance());
            processor.add(mainScreenShot);
            return true;
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return false;
    }

    private final transient CtrlLoopThreadComp ctrlLoopThreadComp = CtrlLoopThreadComp.ofSupplier(this::booleanSupplier, false, 1000);

    /**
     * Create the panel.
     */
    public RuntimeScanPanel() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));

        // 透明
        this.setBackground(null);
        this.setOpaque(false);

        btnOpen = new JButton("选择输出文件夹");
        btnOpen.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jfc.showOpenDialog(btnOpen);
            File f = jfc.getSelectedFile();
            if (f != null && f.isDirectory()) {
                String path = f.toString();
                outPath.setText(path);
            }
        });

        btnConvert = new JButton("打开输出文件夹");
        btnConvert.addActionListener(e -> {
            log.debug("btnConvert click");
            String pathText = outPath.getText();
            if (StringUtils.isBlank(pathText)) {
                return;
            }
            File file = new File(pathText);
            if (!file.exists() && !file.isDirectory()) {
                log.warn("文件夹不存在, 请检查路径是否正确");
                return;
            }
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException ex) {
                log.warn("打开输出文件夹异常, 请检查路径是否正确");
                ex.printStackTrace();
            }
        });

        boardPanel = new JPanel();
        boardPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        boardPanel.setLayout(new BorderLayout(0, 0));
        boardPanel.setBackground(null);
        boardPanel.setOpaque(false);
        boardPanel.add(btnConvert, BorderLayout.EAST);
        boardPanel.add(btnOpen, BorderLayout.WEST);
        boardPanel.add(outPath, BorderLayout.CENTER);
        add(boardPanel, BorderLayout.NORTH);

        final JPanel scanPanel = getScanPanel();
        add(scanPanel, BorderLayout.SOUTH);
    }

    private JPanel getScanPanel() {
        processor.start();

        JButton startOrWakeButton = new JButton("实时扫描");
        startOrWakeButton.addActionListener(e -> {
            ctrlLoopThreadComp.startOrWake();
            log.info("实时扫描");
        });
        JButton pauseScanBtn = new JButton("暂停扫描");
        pauseScanBtn.addActionListener(e -> {
            ctrlLoopThreadComp.pause();
            log.info("暂停扫描");
        });
        JPanel jPanel = new JPanel();
        jPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        jPanel.setLayout(new BorderLayout(0, 0));
        jPanel.setBackground(null);
        jPanel.setOpaque(false);
        jPanel.add(startOrWakeButton, BorderLayout.WEST);
        jPanel.add(pauseScanBtn, BorderLayout.EAST);
        return jPanel;
    }

}
