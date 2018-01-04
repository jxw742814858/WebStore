package cn.iamtudou.kit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件读取类
 */
public class PropKit {

    public static Properties getProp(String fileName) {
        Properties propRes = new Properties();
        try {
            InputStream inputStream = PropKit.class.getClassLoader().getResourceAsStream(fileName);
            propRes.load(inputStream);
            return propRes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
