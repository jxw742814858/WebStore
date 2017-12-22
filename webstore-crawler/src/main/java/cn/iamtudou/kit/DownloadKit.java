package cn.iamtudou.kit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadKit {
    private static Logger log = LoggerFactory.getLogger(DownloadKit.class);

    /**
     * 从网络下载文件
     *
     * @throws MalformedURLException
     */
    public static boolean downloadNet(String imgAddr, String savePath) throws MalformedURLException {
        int bytesum = 0;
        int byteread = 0;

        URL url = new URL(imgAddr);

        try (InputStream inStream = url.openConnection().getInputStream();
             FileOutputStream fs = new FileOutputStream(savePath)) {

            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException ffe) {
            log.error("download file error, msg:{}", ffe.toString());
        } catch (IOException ioe) {
            log.error("download file error, msg:{}", ioe.toString());
        }

        return false;
    }
}
