package com.bluetron.core.bean.scandevice;

import android.os.Parcel;
import android.os.Parcelable;

public class ScanDevice implements Parcelable  {
    private String id;
    private long lastModifyDate;
    private String name;
    private boolean indicator;
    public  ScanDevice(){

    }

    protected ScanDevice(Parcel in) {
        id = in.readString();
        lastModifyDate = in.readLong();
        name = in.readString();
        indicator = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeLong(lastModifyDate);
        dest.writeString(name);
        dest.writeByte((byte) (indicator ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ScanDevice> CREATOR = new Creator<ScanDevice>() {
        @Override
        public ScanDevice createFromParcel(Parcel in) {
            return new ScanDevice(in);
        }

        @Override
        public ScanDevice[] newArray(int size) {
            return new ScanDevice[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(long lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIndicator() {
        return indicator;
    }

    public void setIndicator(boolean indicator) {
        this.indicator = indicator;
    }
}
