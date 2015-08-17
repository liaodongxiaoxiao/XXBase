package com.ldxx.xxbase.common;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by WangZhuo on 2015/6/29.
 */
public class FileDownloadThread extends Thread {
    private String TAG = this.getClass().getSimpleName();

    //下载文件保存路径
    private File saveFile;

    /**
     * 当前下载是否完成
     */
    private boolean isCompleted = false;
    /**
     * 当前下载文件长度
     */
    private int downloadLength = 0;

    private int startPos;
    private int endPos;

    private String hostFilePath;

    public FileDownloadThread(String hostFilePath, int startPos, int endPos, File saveFile) {
        this.saveFile = saveFile;
        this.startPos = startPos;
        this.endPos = endPos;
        this.hostFilePath = hostFilePath;
    }

    @Override
    public void run() {
        BufferedInputStream bis = null;
        RandomAccessFile raf = null;

        try {
            // 构造URL
            URL url = new URL(hostFilePath);
            // 打开连接
            URLConnection connection = url.openConnection();
            connection.setAllowUserInteraction(true);

            //设置当前线程下载的起点、终点
            connection.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
            Log.e(TAG, Thread.currentThread().getName() + "  bytes=" + startPos + "-" + endPos);

            byte[] buffer = new byte[1024];
            bis = new BufferedInputStream(connection.getInputStream());

            raf = new RandomAccessFile(saveFile, "rwd");
            raf.seek(startPos);
            int len;
            while ((len = bis.read(buffer, 0, 1024)) != -1) {
                raf.write(buffer, 0, len);
                downloadLength += len;
            }
            isCompleted = true;
            Log.e(TAG, this.getName()+" task has finished,all size:"
                    + downloadLength);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public int getDownloadLength() {
        return downloadLength;
    }
}
