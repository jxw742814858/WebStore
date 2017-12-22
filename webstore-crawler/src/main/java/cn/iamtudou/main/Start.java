package cn.iamtudou.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Start {
    public static void main(String[] args) {
        System.out.println(getRealPath());
        /*
        int x = 0;
        while (true) {
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Properties properties = getProp("");
            CommandUtil commandUtil = new CommandUtil();
            commandUtil.executeCommand(properties.getProperty("runscript"));
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<String> infos = commandUtil.getStdoutList();
            List<String> list2 = commandUtil.getErroroutList();
            List<AppInfo> appInfos = new ArrayList<AppInfo>();
            if (infos != null && infos.size() > 0) {
                // int a = 0;
                for (String item : infos) {
                    System.out.println(item);
                    String[] arr = item.split(" ");
                    List<String> tempList = new ArrayList<String>();
                    AppInfo appInfo = new AppInfo();
                    for (String temp : arr) {
                        if (!temp.trim().equals("")) {
                            if (tempList.size() >= 11) {
                                tempList.set(10, tempList.get(10) + " " + temp.trim());
                            } else {
                                tempList.add(temp.trim());
                            }

                        }
                    }
                    //USER PID %CPU %MEM VSZ RSS TTY STAT START TIME COMMAND
                    appInfo.setUser(tempList.get(0));
                    appInfo.setPid(tempList.get(1));
                    appInfo.setCpu(tempList.get(2));
                    appInfo.setMem(tempList.get(3));
                    appInfo.setVsz(tempList.get(4));
                    appInfo.setRss(tempList.get(5));
                    appInfo.setTty(tempList.get(6));
                    appInfo.setStat(tempList.get(7));
                    appInfo.setStart(tempList.get(8));
                    appInfo.setTime(tempList.get(9));
                    appInfo.setCommand(tempList.get(10));
                    appInfos.add(appInfo);
                }

            }
            boolean flag = false;
            if (appInfos.size() == 0)
                break;
            x++;
            System.out.println(x);
            for (AppInfo appinfo : appInfos
                    ) {
                if (appinfo.getCommand().contains(properties.getProperty("contains"))) {
                    flag = true;
                    System.out.println("The application is running!");
                    break;
                }
            }
            if (!flag) {
                commandUtil.executeCommand(properties.getProperty("commadn"));
                System.out.println("The application is over,start command is run!");
            }

        }*/
    }

    public static Properties getProp(String fileName) {
        Properties result = new Properties();
        try {
            //InputStream inputStream = Start.class.getClassLoader().getResourceAsStream(fileName);
            InputStream inputStream = new FileInputStream(fileName);
            result.load(inputStream);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String getRealPath() {
        String realPath = System.getProperty("user.dir");
        File file = new java.io.File(realPath);
        realPath = file.getAbsolutePath();
        try {
            realPath = java.net.URLDecoder.decode(realPath, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return realPath;
    }
}