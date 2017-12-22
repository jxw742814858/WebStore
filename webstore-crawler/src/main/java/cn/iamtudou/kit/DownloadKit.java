package cn.iamtudou.kit;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadKit {

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
