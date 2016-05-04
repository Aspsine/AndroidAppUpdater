package com.aspsine.androidappupdater;

/**
 * Created by aspsine on 16/5/3.
 */
public class DownloadException extends Exception {

    public DownloadException(Throwable throwable) {
        super(throwable);
    }

    public DownloadException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
