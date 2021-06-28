package cn.cpf.app.frame;

import cn.cpf.app.global.CompContext;
import com.github.cosycode.common.ext.hub.LazySingleton;
import com.github.cosycode.common.ext.hub.SimpleCode;
import com.github.cosycode.common.thread.AsynchronousProcessor;
import com.github.cosycode.common.util.io.IoUtils;
import com.github.cosycode.ext.se.robot.KeyPressDecorator;
import com.github.cosycode.ext.se.util.DataConvertUtils;
import com.github.cosycode.ext.swing.comp.JField;
import com.github.cosycode.ext.swing.comp.JPathTextField;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/8/11 12:15
 */
@Slf4j
public class KeyInputPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JButton btnOpen = CompContext.registerComponent("selectFile", new JButton());

    private final JButton btnRead = CompContext.registerComponent("printRead", new JButton());

    private final JButton btnReadForBase64 = CompContext.registerComponent("printReadWithBase64", new JButton());

    @Getter
    private final JTextField tfPath = new JPathTextField();

    private final JButton btnStart = CompContext.registerComponent("PrintStart", new JButton());

    private final JButton btnStop = CompContext.registerComponent("printPause", new JButton());

    private final JButton btnClear = CompContext.registerComponent("PrintClear", new JButton());

    @Getter
    private final JField jfInterval = CompContext.registerComponent("interval", new JField("interval"));

    @Getter
    private final JField jfPrepareTime = CompContext.registerComponent("prepareTime", new JField("prepareTime"));

    @Getter
    private final JField jfLength = CompContext.registerComponent("length", new JField("length", true));

    @Getter
    private final JField jfProgress = CompContext.registerComponent("progress", new JField("progress", true));

    @Getter
    private final JField jfStartTime = CompContext.registerComponent("startTime", new JField("startTime", true));

    @Getter
    private final JField jfTimeLeft = CompContext.registerComponent("timeLeft", new JField("timeLeft", true));

    @Getter
    private final JTextArea jfTextArea = new JTextArea();

    private static final LazySingleton<KeyPressDecorator> robotLazySingleton = LazySingleton.of(() -> {
        try {
            return new KeyPressDecorator(new Robot());
        } catch (AWTException e) {
            log.error("new Robot() 失败", e);
        }
        return null;
    });

    /**
     * Create the panel.
     */
    public KeyInputPanel() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));

        // 透明
        this.setBackground(null);
        this.setOpaque(false);

        addListener();

        jfInterval.setText("10");
        jfPrepareTime.setText("5000");

        JPanel showPane = new JPanel();
        showPane.setLayout(new GridLayout(3, 2, 15, 15));
        showPane.add(jfInterval);
        showPane.add(jfPrepareTime);
        showPane.add(jfLength);
        showPane.add(jfProgress);
        showPane.add(jfStartTime);
        showPane.add(jfTimeLeft);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 5, 5, 5));
        panel.add(btnRead);
        panel.add(btnReadForBase64);
        panel.add(btnStart);
        panel.add(btnStop);
        panel.add(btnClear);

        JPanel boardPane = new JPanel();
        boardPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        boardPane.setLayout(new BorderLayout(0, 0));
        boardPane.setBackground(null);
        boardPane.setOpaque(false);
        boardPane.add(btnOpen, BorderLayout.WEST);
        boardPane.add(tfPath, BorderLayout.CENTER);
        boardPane.add(showPane, BorderLayout.NORTH);
        boardPane.add(panel, BorderLayout.SOUTH);

        add(boardPane, BorderLayout.NORTH);

        jfTextArea.setAutoscrolls(true);
        jfTextArea.setLineWrap(true);
        jfTextArea.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane(jfTextArea);
        // 分别设置水平和垂直滚动条自动出现
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel logPane = new JPanel();
        logPane.setLayout(new BorderLayout());
        logPane.add(jScrollPane, BorderLayout.CENTER);
        add(logPane, BorderLayout.CENTER);
    }

    @SuppressWarnings("java:S3776")
    private void addListener() {
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

        btnReadForBase64.addActionListener(e -> {
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
                final String text = DataConvertUtils.fileToBase64(file);
                jfTextArea.setText(text);
            } catch (IOException ex) {
                log.error("读取文件失败", ex);
            }
        });

        btnRead.addActionListener(e -> {
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
                String text = IoUtils.readStringFromInputStream(new FileInputStream(file));
                text = text.replaceAll("(?<!\r)\n", "\r\n");
                jfTextArea.setText(text);
            } catch (IOException ex) {
                log.error("读取文件失败", ex);
            }
        });

        jfTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // 当attribute改变时触发
                // 有待继续探索......
                log.debug("changedUpdate");
            }

            private void changed() {
                int length = jfTextArea.getText().getBytes().length;
                jfLength.setText(String.valueOf(length));
            }
        });

        btnStart.addActionListener(e -> {
            log.debug("准备打印, 请找寻焦点, 并将输入法调至英文");
            // 等待时间
            int sleepTime = jfPrepareTime.get(s -> SimpleCode.simpleException(() -> Integer.parseInt(s), 500, "prepareTime 转换失败", false));
            if (sleepTime < 2000 || sleepTime > 30000) {
                log.warn("等待时间需要在 2000 - 30000 以内");
                return;
            }
            // 字符打印时间间隔
            final int intervalTime = jfInterval.get(s -> SimpleCode.simpleException(() -> Integer.parseInt(s), 10, "intervalTime 转换失败", false));
            if (intervalTime < 3 || intervalTime > 1000) {
                log.warn("等待时间需要在 3 - 1000 以内");
                return;
            }

            final String text = jfTextArea.getText().replace("\r\n", "\n");
            int length = text.length();
            if (length > 0) {
                new Thread(() -> {
                    KeyPressDecorator instance = robotLazySingleton.instance();
                    AsynchronousProcessor<Character> asynchronousProcessor = instance.getAsynchronousProcessor();
                    asynchronousProcessor.wake();
                    asynchronousProcessor.setMillisecond(intervalTime);
                    jfStartTime.setText(LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
                    // 打印之前等待5秒, 为了找寻打印的焦点
                    SimpleCode.runtimeException(() -> Thread.sleep(sleepTime));
                    // 打印
                    instance.print(text);
                    // 每隔 50 mm, 更新打印进度
                    BlockingQueue<Character> queue = asynchronousProcessor.getBlockingQueue();
                    while (!queue.isEmpty()) {
                        int size = queue.size();
                        jfProgress.setText((length - size) + " / " + length);
                        jfTimeLeft.setText(String.valueOf(size * intervalTime / 1000));
                        SimpleCode.runtimeException(() -> Thread.sleep(50));
                    }
                    jfProgress.setText(length + " / " + length);
                    log.info("{} :: 进度线程结束", Thread.currentThread().getName());
                }).start();
            }
        });

        btnStop.addActionListener(e -> {
            AsynchronousProcessor<Character> asynchronousProcessor = robotLazySingleton.instance().getAsynchronousProcessor();
            if (asynchronousProcessor.isSuspend()) {
                log.debug("准备打印, 请找寻焦点, 并将输入法调至英文");
                // 等待时间
                int sleepTime = jfPrepareTime.get(s -> SimpleCode.simpleException(() -> Integer.parseInt(s), 500, "prepareTime 转换失败", false));
                if (sleepTime < 2000 || sleepTime > 30000) {
                    log.warn("等待时间需要在 2000 - 30000 以内");
                    return;
                }
                // 字符打印时间间隔
                final int intervalTime = jfInterval.get(s -> SimpleCode.simpleException(() -> Integer.parseInt(s), 10, "intervalTime 转换失败", false));
                if (intervalTime < 3 || intervalTime > 1000) {
                    log.warn("等待时间需要在 3 - 1000 以内");
                    return;
                }
                new Thread(() -> {
                    SimpleCode.runtimeException(() -> Thread.sleep(sleepTime));
                    asynchronousProcessor.setMillisecond(intervalTime);
                    asynchronousProcessor.wake();
                    log.debug("{} :: 线程已经被唤醒", Thread.currentThread().getName());
                }).start();
                btnStop.setText("pause");
            } else {
                asynchronousProcessor.pause();
                btnStop.setText("resume");
            }
        });

        btnClear.addActionListener(e -> {
            jfTextArea.setText("");
            robotLazySingleton.instance().getAsynchronousProcessor().getBlockingQueue().clear();
        });
    }

}
