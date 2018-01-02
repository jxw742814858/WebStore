package cn.iamtudou.kit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParaKit {
    private static Logger log = LoggerFactory.getLogger(ParaKit.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getImgType(String imgUrl) {
        String[] types = {
                ".jpg",
                ".jpeg",
                ".png",
                ".gif",
                ".bmp"
        };

        Matcher matcher = null;

        for (String t : types) {
            matcher = Pattern.compile(t, Pattern.CASE_INSENSITIVE).matcher(imgUrl);
            if (matcher.find())
                return t;
        }

        return null;
    }
}
