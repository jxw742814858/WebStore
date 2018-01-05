package cn.iamtudou.kit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;
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

    /**
     * 获取前两天的日期
     * @return
     */
    public static Set<String> getLastDays() {
        String day_1 = null, day_2 = null;
        Set<String> days = new HashSet<>();

        Calendar cal_1 = Calendar.getInstance();
        cal_1.add(Calendar.DATE, -1);
        day_1 = String.format("%s-%s-%s", cal_1.get(Calendar.YEAR),
                cal_1.get(Calendar.MONTH) + 1, cal_1.get(Calendar.DAY_OF_MONTH)); //前一天日期

        Calendar cal_2 = Calendar.getInstance();
        cal_2.add(Calendar.DATE, -2);
        day_2 = String.format("%s-%s-%s", cal_2.get(Calendar.YEAR),
                cal_2.get(Calendar.MONTH) + 1, cal_2.get(Calendar.DAY_OF_MONTH)); //前两天日期

        days.add(day_1);
        days.add(day_2);

        return days;
    }
}
