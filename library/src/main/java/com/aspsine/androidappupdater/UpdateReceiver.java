package com.aspsine.androidappupdater;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by aspsine on 16/5/4.
 */
public abstract class UpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(UpdateService.ACTION_UPDATE)) {
            Status status = intent.getParcelableExtra(UpdateService.EXTRA_STATUS);
            int flag = status.status;
            switch (flag) {
                case Status.STATUS_DOWNLOAD_START:
                    onDownloadStarted();
                    break;
                case Status.STATUS_DOWNLOAD_PROGRESSING:
                    onDownloadProgressing(status.finished, status.total, status.progress);
                    break;
                case Status.STATUS_DOWNLOAD_COMPLETE:
                    onDownloadCompleted(status.apkPath);
                    break;
                case Status.STATUS_DOWNLOAD_FAILED:
                    onDownloadFailed();
                    break;
                case Status.STATUS_DOWNLOAD_CANCELED:
                    onDownloadCanceled();
                    break;
            }
        }
    }

    protected abstract void onDownloadStarted();

    protected abstract void onDownloadProgressing(int finished, int total, int progress);

    protected abstract void onDownloadCompleted(String apkPath);

    protected abstract void onDownloadFailed();

    protected abstract void onDownloadCanceled();
}
