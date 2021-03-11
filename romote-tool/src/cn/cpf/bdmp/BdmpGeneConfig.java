package cn.cpf.bdmp;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * <b>Description : </b> 二进制图片配置类
 *
 * @author CPF
 * Date: 2020/5/19 18:15
 */
public class BdmpGeneConfig {
    /**
     * 代表像素图片 8 * 4
     */
    private int type = 0;
    /**
     * 版本号: 8 * 4
     */
    private int version = 1;
    /**
     * 版本号: 8 * 4
     */
    private long versionTime = 1589939253407L;
    /**
     * 一行像素数量
     */
    private int rowPixelCnt;
    /**
     * 点阵像素宽度
     */
    private int pixelSideWidth;
    /**
     * 点阵像素高度
     */
    private int pixelSideHeight;
    /**
     * 映射颜色
     */
    private Color[] mappingColor;
    /**
     * 上右下左的边缘宽度(灰色)
     */
    private int[] marginLen;

    @Override
    public String toString() {
        return "BdmpGeneConfig{" +
                "type=" + type +
                ", version=" + version +
                ", versionTime=" + versionTime +
                ", rowPixelCnt=" + rowPixelCnt +
                ", pixelSideWidth=" + pixelSideWidth +
                ", pixelSideHeight=" + pixelSideHeight +
                ", marginLen=" + Arrays.toString(marginLen) +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(long versionTime) {
        this.versionTime = versionTime;
    }

    public int getRowPixelCnt() {
        return rowPixelCnt;
    }

    public void setRowPixelCnt(int rowPixelCnt) {
        this.rowPixelCnt = rowPixelCnt;
    }

    public int getPixelSideWidth() {
        return pixelSideWidth;
    }

    public void setPixelSideWidth(int pixelSideWidth) {
        this.pixelSideWidth = pixelSideWidth;
    }

    public int getPixelSideHeight() {
        return pixelSideHeight;
    }

    public void setPixelSideHeight(int pixelSideHeight) {
        this.pixelSideHeight = pixelSideHeight;
    }

    public Color[] getMappingColor() {
        return mappingColor;
    }

    public void setMappingColor(Color[] mappingColor) {
        this.mappingColor = mappingColor;
    }

    public int[] getMarginLen() {
        return marginLen;
    }

    public void setMarginLen(int[] marginLen) {
        this.marginLen = marginLen;
    }

    public void setMargin(int margin) {
        setMargin(margin, margin, margin, margin);
    }

    public void setMargin(int top, int right, int bottom, int left) {
        marginLen = new int[]{top, right, bottom, left};
    }

    public void checkWithThrow() {
        Objects.requireNonNull(marginLen, "marginLen cannot be null");
        BdmpUtils.isTrue(rowPixelCnt > 0, "margin require > 0: %s", rowPixelCnt);
        BdmpUtils.isTrue(pixelSideWidth > 0, "the pixelSideWidth:%s require > 0", pixelSideWidth);
        BdmpUtils.isTrue(pixelSideHeight > 0, "the pixelSideHeight:%s require > 0", pixelSideHeight);
        Objects.requireNonNull(mappingColor, "mappingColor cannot be null");

        int bitCnt = (int) (Math.log(mappingColor.length) / Math.log(2));
        BdmpUtils.isTrue(((int) Math.pow(2, bitCnt)) == mappingColor.length, "marginLen should be a power of 2");
        BdmpUtils.isTrue(Arrays.stream(mappingColor).noneMatch(Objects::isNull), "mappingColor中不能为空");
    }

}
