package cn.iamtudou.kit;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncodeKit {

    /**
     * 仅对文本中的中文字符进行encode编码
     * @param str 原文本对象
     * @return
     */
    public static String encodeCn(String str) {
        try {
            Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]").matcher(str);
            while (matcher.find()) {
                String tmp = matcher.group();
                str = str.replaceAll(tmp, URLEncoder.encode(tmp, "UTF-8"));
            }
            return str;
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }

        return null;
    }
}
