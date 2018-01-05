package cn.iamtudou.kit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropKit {

    public static Properties getProp(String fileName) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = PropKit.class.getClassLoader().getResourceAsStream(fileName);
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
