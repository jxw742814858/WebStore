package cn.iamtudou.kit;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropKit {

    public static Properties getProp(String fileName) {
        Properties properties = new Properties();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(
                    PropKit.class.getClassLoader().getResourceAsStream(fileName), "UTF-8");
            properties.load(inputStreamReader);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
