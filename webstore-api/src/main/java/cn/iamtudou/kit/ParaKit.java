package cn.iamtudou.kit;

import java.util.Calendar;

public class ParaKit {

    public static String gnrTodayKey() {
        String key = null;
        Calendar cal = Calendar.getInstance();
        key = String.format("%s-%s-%s", cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));

        return key;
    }

    public static String fomatStr(String dataStr) {
        return "[" + dataStr + "]";
    }
}
