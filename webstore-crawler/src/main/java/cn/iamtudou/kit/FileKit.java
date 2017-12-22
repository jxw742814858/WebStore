package cn.iamtudou.kit;

import java.io.File;

public class FileKit {

    /**
     * 文件夹操作
     * @param path
     */
    public static void folderOp(String path) {
        File file = new File(path);
        if (!file.exists())
            file.mkdir();
    }
}
