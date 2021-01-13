package cn.cpf.app.frame;

import cn.cpf.app.comp.JPathTextField;
import cn.cpf.app.comp.StandardTable;
import cn.cpf.app.global.ConfigHelper;
import cn.cpf.app.util.BdmpOutUtils;
import cn.cpf.app.util.OsUtils;
import com.github.cosycode.bdmp.BdmpHeader;
import com.github.cosycode.bdmp.BdmpSource;
import com.github.cosycode.common.base.SupplierWithThrow;
import com.github.cosycode.common.ext.bean.DoubleBean;
import com.github.cosycode.common.ext.bean.Record;
import com.github.cosycode.common.ext.hub.LazySingleton;
import com.github.cosycode.common.ext.hub.SimpleCode;
import com.github.cosycode.common.thread.AsynchronousProcessor;
import com.github.cosycode.common.thread.CtrlLoopThreadComp;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

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
    private final JPanel boardPanel;

    @Getter
    private final JButton btnOpen;

    @Getter
    private final JButton btnConvert;

    @Getter
    private final JTextField outPath = new JPathTextField(ConfigHelper.getScanSaveDirPath());

    private final transient LazySingleton<StandardTable<Record>> tableLazySingleton = LazySingleton.of(() -> {
        final StandardTable<Record> standardTable = new StandardTable<>();
        standardTable.setColumnConfigList(Arrays.asList(
                new StandardTable.ColumnConfig("tag", "名称"),
                new StandardTable.ColumnConfig("length", "长度"),
                new StandardTable.ColumnConfig("deTime", "解析时间"),
                new StandardTable.ColumnConfig("path", "路径")
        ));
        return standardTable;
    });

    public final transient AsynchronousProcessor<BufferedImage> processor = AsynchronousProcessor.ofConsumer((BufferedImage image) -> {
        String text = outPath.getText();
        if (StringUtils.isBlank(text) || !new File(text).isDirectory()) {
            text = ConfigHelper.getScanSaveDirPath();
            outPath.setText(text);
        }
        final String saveDir = text;
        final DoubleBean<Boolean, BdmpHeader> booleanBdmpRecInfoDoubleBean = SimpleCode.runtimeException(() -> BdmpOutUtils.convertBinPicToType(image, BdmpSource.SourceType.TYPE_FILE, saveDir, false));
        if (booleanBdmpRecInfoDoubleBean == null) {
            return;
        }
        final Boolean o1 = booleanBdmpRecInfoDoubleBean.getO1();
        if (o1 == null || !o1) {
            // 没有写入成功
            return;
        }
        // 写入成功
        Record record = new Record();
        final BdmpHeader bdmpHeader = booleanBdmpRecInfoDoubleBean.getO2();
        record.put("tag", bdmpHeader.getTag());
        record.put("length", bdmpHeader.getContentLength());
        record.put("deTime", new Date());
        record.put("path", saveDir);
        tableLazySingleton.instance().add(record);
    }).setName("异步图片识别线程");


    private final transient LazySingleton<Robot> robotSinTon = LazySingleton.of(() -> SimpleCode.runtimeException((SupplierWithThrow<Robot, AWTException>) Robot::new, "创建Robot对象失败"));

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

    private final transient CtrlLoopThreadComp ctrlLoopThreadComp = CtrlLoopThreadComp.ofSupplier(this::booleanSupplier).setName("实时截取主屏幕可控线程").setMillisecond(1000);

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

        final JPanel scanPanel = getScanPanel();

        add(boardPanel, BorderLayout.NORTH);
        add(tableLazySingleton.instance(), BorderLayout.CENTER);
        add(scanPanel, BorderLayout.SOUTH);
    }

    private JPanel getScanPanel() {

        JButton startOrWakeButton = new JButton("实时扫描");
        startOrWakeButton.addActionListener(e -> {
            processor.startOrWake();
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
