package com.aspsine.androidappupdater;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aspsine on 16/5/3.
 */
public class Status implements Parcelable{
    public static final int STATUS_DOWNLOAD_START = Integer.MAX_VALUE;
    public static final int STATUS_DOWNLOAD_PROGRESSING = Integer.MAX_VALUE - 1;
    public static final int STATUS_DOWNLOAD_COMPLETE = Integer.MAX_VALUE - 2;
    public static final int STATUS_DOWNLOAD_FAILED = Integer.MAX_VALUE - 3;
    public static final int STATUS_DOWNLOAD_CANCELED = Integer.MAX_VALUE - 4;

    public int status;
    public int progress;
    public int total;
    public int finished;
    public String apkPath;

    public Status() {
    }

    protected Status(Parcel in) {
        status = in.readInt();
        progress = in.readInt();
        total = in.readInt();
        finished = in.readInt();
        apkPath = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeInt(progress);
        dest.writeInt(total);
        dest.writeInt(finished);
        dest.writeString(apkPath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };

    public void clear() {
        status = 0;
        progress = 0;
        total = 0;
        finished = 0;
        apkPath = null;
    }


}
