package cn.iamtudou.kit;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 图片工具
 */
public class ImageKit {
    private static Properties prop = PropKit.getProp("config.properties");

    /**
     * 图片压缩处理
     *
     * @param filePath 原图片地址
     * @return 最终图片地址
     */
    public static String imgOperate(String filePath) {
        File file = new File(filePath);
        String f_filePath = filePath.replace("_tmp", "");
        File f_file = new File(f_filePath);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        double s_width = bi.getWidth(); //原图片宽度
        double s_height = bi.getHeight(); //原图片高度
        double rate = s_width / s_height;
        int width = Integer.valueOf(prop.getProperty("img.width"));

        /*当下载的图片尺寸大于设定的最大尺寸时，压缩生成新图片后删除原图片*/
        if (s_width > width) {
            try {
                thumbnail_w_h(file, width, new Double(width / rate).intValue(), new FileOutputStream(f_file));
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            file.renameTo(f_file);

        return f_filePath;
    }

    /**
     * 按照 宽高 比例压缩
     *
     * @param img
     * @param width
     * @param height
     * @param out
     * @throws IOException
     */
    private static void thumbnail_w_h(File img, int width, int height, OutputStream out) throws IOException {
        BufferedImage bi = ImageIO.read(img);
        double srcWidth = bi.getWidth(); // 源图宽度
        double srcHeight = bi.getHeight(); // 源图高度

        double scale = 1;

        if (width > 0) {
            scale = width / srcWidth;
        }
        if (height > 0) {
            scale = height / srcHeight;
        }
        if (width > 0 && height > 0) {
            scale = height / srcHeight < width / srcWidth ? height / srcHeight : width / srcWidth;
        }
        thumbnail(img, (int) (srcWidth * scale), (int) (srcHeight * scale), out);
    }

    /**
     * 按照固定宽高原图压缩
     *
     * @param img
     * @param width
     * @param height
     * @param out
     * @throws IOException
     */
    private static void thumbnail(File img, int width, int height, OutputStream out) throws IOException {
        BufferedImage BI = ImageIO.read(img);
        Image image = BI.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.setColor(Color.RED);
        g.drawImage(image, 0, 0, null); // 绘制处理后的图
        g.dispose();
        ImageIO.write(tag, "JPEG", out);
    }
}
