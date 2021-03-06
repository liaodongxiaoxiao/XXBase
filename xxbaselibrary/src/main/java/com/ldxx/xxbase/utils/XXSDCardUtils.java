package com.ldxx.xxbase.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by WANGZHUO on 2015/4/15.
 */
public class XXSDCardUtils {
    private XXSDCardUtils() {
        /** cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

    }

    /**
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * @return
     */
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
            //
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            //
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * @param filePath
     * @return
     */
    public static long getFreeBytes(String filePath) {
        //
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {//
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }
}
