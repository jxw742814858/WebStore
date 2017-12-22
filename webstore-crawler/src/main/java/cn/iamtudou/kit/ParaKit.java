package cn.iamtudou.kit;

import cn.iamtudou.constants.Const;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParaKit {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 判断是否为资讯类url
     *
     * @param url url
     * @return
     */
    public static boolean isNewsUrl(String url) {
        Matcher matcher = null;
        for (String reg : Const.REG_URL) {
            matcher = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(url);
            if (matcher.find())
                return true;
        }
        return false;
    }

    /**
     * 页面时间转时间戳
     *
     * @param str
     * @return
     */
    public static long getTimestamp(String str) {
        long timestmp = 0L;
        String gp = null;
        Matcher matcher = null;
        for (String reg : Const.REG_TIME) {
            matcher = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(str);
            while (matcher.find())
                gp = matcher.group("group");
        }

        try {
            timestmp = sdf.parse(gp).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timestmp;
    }

    /**
     * 匹配资讯版块名
     *
     * @param str
     * @return
     */
    public static String getPlate(String str) {
        String plate = null;
        Matcher matcher = null;
        for (String reg : Const.REG_PLATE) {
            matcher = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(str);
            while (matcher.find())
                plate = matcher.group("group");
        }

        return plate;
    }
}
