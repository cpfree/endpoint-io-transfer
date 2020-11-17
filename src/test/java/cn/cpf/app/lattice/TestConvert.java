package cn.cpf.app.lattice;

import com.github.cpfniliu.common.util.io.FileSystemUtils;
import com.github.cpfniliu.common.util.io.IoUtils;
import lombok.NonNull;

import java.io.*;
import java.nio.charset.Charset;

import static com.github.cpfniliu.common.util.io.IoUtils.insureFileExist;

public class TestConvert {

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\Private\\read-now\\red";
        FileSystemUtils.fileDisposeFromDir(new File(path), file -> {
            final FileReader reader;
            try {
                reader = new FileReader(file);
                if ("UTF8".endsWith(reader.getEncoding())) {
                    System.out.println(file.getPath());
                    final String x = IoUtils.readFile(file.getAbsolutePath());
                    IoUtils.writeStringToOutputStream(new FileOutputStream(file), x, Charset.forName("GBK"));
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
        insureFileExist(file);

        // 写入文件
        try (final FileWriter writer = new FileWriter(file)){
            System.out.println(writer.getEncoding());
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
