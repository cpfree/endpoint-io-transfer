package cn.cpf.bdmp;

import cn.cpf.util.SupplierWithThrow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class BdmpSource {

    private SourceType sourceType;
    private String name;
    private byte[] content;
    private SupplierWithThrow<byte[], IOException> dataSupplier;

    public BdmpSource(SourceType sourceType, String name, SupplierWithThrow<byte[], IOException> dataSupplier) {
        this.sourceType = sourceType;
        this.name = name;
        this.dataSupplier = dataSupplier;
    }

    public static BdmpSource geneByFile(File file) {
        return new BdmpSource(SourceType.TYPE_FILE, file.getName(), () -> {
            final long length = file.length();
            BdmpUtils.isTrue(length < Integer.MAX_VALUE, "文件过大");
            byte[] bytes = new byte[(int) length];
            // 写入文件
            try (FileInputStream in = new FileInputStream(file)) {
                final int read = in.read(bytes);
                BdmpUtils.isTrue(read == (int) length, "读取错误");
            }
            return bytes;
        });
    }

    public static BdmpSource geneByClipboard(String name, byte[] content) {
        return new BdmpSource(SourceType.TYPE_CLIPBOARD, name, () -> content);
    }

    public void checkWithThrow() throws IOException {
        Objects.requireNonNull(getContent(), "无内容");
        Objects.requireNonNull(getSourceType(), "无SourceType");
    }

    public byte[] getContent() throws IOException {
        if (content == null) {
            content = dataSupplier.get();
        }
        return content;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public String getName() {
        return name;
    }

    public SupplierWithThrow<byte[], IOException> getDataSupplier() {
        return dataSupplier;
    }

    /**
     * 源类型: {文件地址, 网络地址, 二进制流, 剪贴板}
     */
    public enum SourceType {
        TYPE_FILE, TYPE_CONTENT, TYPE_CLIPBOARD
    }
}
