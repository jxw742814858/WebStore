package cn.iamtudou.kit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PropKit {

    public static String file2String(String fileName) {
        InputStream inputStream = PropKit.class.getClassLoader().getResourceAsStream(fileName);
        try {
            return inputStream2String(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }
}
