package cn.cpf.bdmp;

import cn.cpf.util.BinPicUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <b>Description : </b> 生成 Bdmp 图片工具类.
 * 因为当前类可能需要手动输入到指定环境, 因此当前类中的代码, 以及其调用到的代码不使用任何其它非JRE官方jar包
 *
 * @author CPF
 * Date: 2020/5/19 15:40
 */
public class BdmpHandle {

    private BdmpHandle() {
    }

    /**
     * 生成图片宽度为 2 * borderLength +
     *
     * @param file            转换文件路径
     * @param rowPxNum        一行像素个数
     * @param pixelSideLength 像素宽度
     * @param margin          margin长度,
     * @param powerOf2        2的指数幂, 可选值为 1, 2, 4, 8, 以及8的倍数,
     *                        像素颜色类型为 2 ^ powerOf2 个,
     *                        如果为 1, 像素颜色为  2种, 每 1 个bit作为一个像素,
     *                        如果为 2, 像素颜色为  4种, 每 2 个bit作为一个像素存储.
     *                        如果为 4, 像素颜色为 16种, 每 4 个bit作为一个像素存储.
     *                        如果为 8, 像素颜色为256种, 每 8 个bit作为一个像素存储.
     *                        如果为8n, 像素颜色为 2 ^ 8n 种, 每 8n 个bit作为一个像素存储.
     * @throws IOException 写入文件和读取文件流异常
     */
    public static BufferedImage convertFileToBdmp(File file, int rowPxNum, int pixelSideLength, int margin, byte powerOf2) throws IOException {
        BdmpGeneConfig bdmpGeneConfig = new BdmpGeneConfig();
        bdmpGeneConfig.setMargin(margin);
        bdmpGeneConfig.setRowPixelCnt(rowPxNum);
        bdmpGeneConfig.setPixelSideWidth(pixelSideLength);
        bdmpGeneConfig.setPixelSideHeight(pixelSideLength);
        bdmpGeneConfig.setMappingColor(BdmpUtils.getPxType(powerOf2));
        final BdmpSource bdmpSource = BdmpSource.geneByFile(file);
        return PixelPngDrawer.geneRatePixelPng(new BdmpGeneInfo(bdmpGeneConfig, bdmpSource));
    }

    /**
     * @param filePath 文件路径
     * @param savePath 存储路径
     * @param rowPxNum 一行像素个数
     * @param pxWidth  像素宽度
     * @param margin   margin长度
     * @param powerOf2 2的指数幂, 可选值为 1, 2, 4, 8, 以及8的倍数,
     *                 像素颜色类型为 2 ^ powerOf2 个,
     *                 如果为 1, 像素颜色为  2种, 每 1 个bit作为一个像素,
     *                 如果为 2, 像素颜色为  4种, 每 2 个bit作为一个像素存储.
     *                 如果为 4, 像素颜色为 16种, 每 4 个bit作为一个像素存储.
     *                 如果为 8, 像素颜色为256种, 每 8 个bit作为一个像素存储.
     *                 如果为8n, 像素颜色为 2 ^ 8n 种, 每 8n 个bit作为一个像素存储.
     * @throws IOException 写入文件和读取文件流异常
     */
    public static void convertFileToBdmp(String filePath, String savePath, int rowPxNum, int pxWidth, int margin, byte powerOf2) throws IOException {
        File file = new File(filePath);
        final BufferedImage image = convertFileToBdmp(file, rowPxNum, pxWidth, margin, powerOf2);
        // 保存图片 JPEG表示保存格式
        ImageIO.write(image, "png", new FileOutputStream(savePath));
        BinPicUtils.logInfo("pixel end " + filePath);
    }

    /**
     * @param filePath 文件路径
     * @param rowPxNum 一行像素个数
     * @param pxWidth  像素宽度
     * @param margin   margin长度
     * @param powerOf2 2的指数幂, 可选值为 1, 2, 4, 8, 以及8的倍数,
     *                 像素颜色类型为 2 ^ powerOf2 个,
     *                 如果为 1, 像素颜色为  2种, 每 1 个bit作为一个像素,
     *                 如果为 2, 像素颜色为  4种, 每 2 个bit作为一个像素存储.
     *                 如果为 4, 像素颜色为 16种, 每 4 个bit作为一个像素存储.
     *                 如果为 8, 像素颜色为256种, 每 8 个bit作为一个像素存储.
     *                 如果为8n, 像素颜色为 2 ^ 8n 种, 每 8n 个bit作为一个像素存储.
     * @throws IOException 写入文件和读取文件流异常
     */
    public static void convertFileToBdmp(String filePath, int rowPxNum, int pxWidth, int margin, byte powerOf2) throws IOException {
        convertFileToBdmp(filePath, filePath + ".png", rowPxNum, pxWidth, margin, powerOf2);
    }

}