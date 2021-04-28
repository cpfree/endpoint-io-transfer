package cn.cpf.app.lattice;

import com.github.cosycode.common.util.io.FileSystemUtils;
import lombok.NonNull;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TestConvert {

    public static void main(String[] args) throws IOException {
        final File file = new File("C:\\Users\\Private\\read\\read-all\\read-creeper\\novel0");
        FileSystemUtils.fileDisposeFromDir(file, f -> {
            try {
                final String charset = charset(f);
                System.out.println(f.getName() + " " + charset);

                if ("UTF-8".equals(charset)) {
                    byte[] bytes = new byte[(int) file.length()];
                    final int read = new FileInputStream(file).read(bytes);
                    assert read != bytes.length;
                    final String s1 = new String(bytes, StandardCharsets.UTF_8);
                    final byte[] gbks = s1.getBytes("GBK");
                    new FileOutputStream(file).write(gbks);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, null);
    }

    /**
     * 往 savePath 路径 写入文件, 如果没有则新增
     *
     * @param savePath 写入路径
     * @param content 内容
     *
     */
    public static void writeFile(@NonNull String savePath, @NonNull String content) {
        final File file = new File(savePath);
        FileSystemUtils.insureFileExist(file);

        // 写入文件
        try (final FileWriter writer = new FileWriter(file)){
            System.out.println(writer.getEncoding());
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws FileNotFoundException {
        File file = new File("D:\\Users\\CPF\\Desktop\\fpp_a0.sql");
        final String test = test(file);
        System.out.println(test);
    }

    public static String test(File file) throws FileNotFoundException {
        FileReader reader = new FileReader(file);
        System.out.println(reader.getEncoding());
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 判断文本文件的字符集，文件开头三个字节表明编码格式。
     * <a href="http://blog.163.com/wf_shunqiziran/blog/static/176307209201258102217810/">参考的博客地址</a>
     *
     * @param file 文件
     * @return 文件的编码, 若文件不存在则返回 EMPTY_STRING
     */
    @SuppressWarnings("all")
    public static String charset(File file) throws IOException {
        if (!file.exists()) {
            return "";
        }
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))){
            boolean checked = false;
            ;
            bis.mark(0); // 读者注： bis.mark(0);修改为 bis.mark(100);我用过这段代码，需要修改上面标出的地方。
            // Wagsn注：不过暂时使用正常，遂不改之
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                bis.close();
                return charset; // 文件编码为 ANSI
            } else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE"; // 文件编码为 Unicode
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE"; // 文件编码为 Unicode big endian
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8"; // 文件编码为 UTF-8
                checked = true;
            }
            bis.reset();
            if (!checked) {
                while ((read = bis.read()) != -1) {
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80 - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) { // 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
        }
        return charset;
    }
}
