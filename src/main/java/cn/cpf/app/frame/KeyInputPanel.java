package cn.cpf.app.frame;

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

    private final JButton btnOpen = new JButton("open");

    private final JButton btnRead = new JButton("read");

    private final JButton btnReadForBase64 = new JButton("BaseConvertRead");

    @Getter
    private final JTextField tfPath = new JPathTextField();

    private final JButton btnStart = new JButton("start print");

    private final JButton btnStop = new JButton("pause");

    private final JButton btnClear = new JButton("清空");

    @Getter
    private final JField interval = new JField("interval");

    @Getter
    private final JField prepareTime = new JField("prepareTime");

    @Getter
    private final JField jfLength = new JField("length", true);

    @Getter
    private final JField progress = new JField("progress", true);

    @Getter
    private final JTextArea textArea = new JTextArea();

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

        interval.setText("10");
        prepareTime.setText("5000");

        JPanel showPane = new JPanel();
        showPane.setLayout(new GridLayout(2, 2, 15, 15));
        showPane.add(interval);
        showPane.add(prepareTime);
        showPane.add(jfLength);
        showPane.add(progress);

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

        textArea.setAutoscrolls(true);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane(textArea);
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
                textArea.setText(text);
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
                textArea.setText(text);
            } catch (IOException ex) {
                log.error("读取文件失败", ex);
            }
        });

        textArea.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void changedUpdate(DocumentEvent e){
                // 当attribute改变时触发
                // 有待继续探索......
                log.debug("changedUpdate");
            }

            private void changed() {
                int length = textArea.getText().getBytes().length;
                jfLength.setText(String.valueOf(length));
            }
        });

        btnStart.addActionListener(e -> {
            log.debug("准备打印, 请找寻焦点, 并将输入法调至英文");
            // 等待时间
            int sleepTime = prepareTime.get(s -> SimpleCode.simpleException(() -> Integer.parseInt(s), 500, "prepareTime 转换失败", false, false));
            if (sleepTime < 2000 || sleepTime > 30000) {
                log.warn("等待时间需要在 2000 - 30000 以内");
                return;
            }
            // 字符打印时间间隔
            final int intervalTime = interval.get(s -> SimpleCode.simpleException(() -> Integer.parseInt(s), 10, "intervalTime 转换失败", false, false));
            if (intervalTime < 3 || intervalTime > 1000) {
                log.warn("等待时间需要在 3 - 1000 以内");
                return;
            }

            final String text = textArea.getText().replace("\r\n", "\n");
            int length = text.length();
            if (length > 0) {
                new Thread(() -> {
                    KeyPressDecorator instance = robotLazySingleton.instance();
                    AsynchronousProcessor<Character> asynchronousProcessor = instance.getAsynchronousProcessor();
                    asynchronousProcessor.wake();
                    asynchronousProcessor.setMillisecond(intervalTime);
                    // 打印之前等待5秒, 为了找寻打印的焦点
                    SimpleCode.runtimeException(() -> Thread.sleep(sleepTime));
                    // 打印
                    instance.print(text);
                    // 每隔 20 mm, 更新打印进度
                    BlockingQueue<Character> queue = asynchronousProcessor.getBlockingQueue();
                    while (!queue.isEmpty()) {
                        int size = queue.size();
                        progress.setText((length - size) + " / " + length);
                        SimpleCode.runtimeException(() -> Thread.sleep(20));
                    }
                    progress.setText(length + " / " + length);
                    log.info( "{} :: 进度线程结束", Thread.currentThread().getName());
                }).start();
            }
        });

        btnStop.addActionListener(e -> {
            AsynchronousProcessor<Character> asynchronousProcessor = robotLazySingleton.instance().getAsynchronousProcessor();
            if (asynchronousProcessor.isSuspend()) {
                log.debug("准备打印, 请找寻焦点, 并将输入法调至英文");
                // 等待时间
                int sleepTime = prepareTime.get(s -> SimpleCode.simpleException(() -> Integer.parseInt(s), 500, "prepareTime 转换失败", false, false));
                if (sleepTime < 2000 || sleepTime > 30000) {
                    log.warn("等待时间需要在 2000 - 30000 以内");
                    return;
                }
                // 字符打印时间间隔
                final int intervalTime = interval.get(s -> SimpleCode.simpleException(() -> Integer.parseInt(s), 10, "intervalTime 转换失败", false, false));
                if (intervalTime < 3 || intervalTime > 1000) {
                    log.warn("等待时间需要在 3 - 1000 以内");
                    return;
                }
                new Thread(() -> {
                    SimpleCode.runtimeException(() -> Thread.sleep(sleepTime));
                    asynchronousProcessor.setMillisecond(intervalTime);
                    asynchronousProcessor.wake();
                    log.debug( "{} :: 线程已经被唤醒", Thread.currentThread().getName());
                }).start();
                btnStop.setText("pause");
            } else {
                asynchronousProcessor.pause();
                btnStop.setText("resume");
            }
        });

        btnClear.addActionListener(e -> {
            textArea.setText("");
            robotLazySingleton.instance().getAsynchronousProcessor().getBlockingQueue().clear();
        });
    }

}
