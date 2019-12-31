package com.bluetron.core.bean.scandevice;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.style.TabStopSpan;

import com.bluetron.core.bean.task.TaskListResponse;

public class ScanDevice implements Parcelable  {

    private TaskListResponse.device device;
    private boolean indicator;

    protected ScanDevice(Parcel in) {
        device = in.readParcelable(TaskListResponse.device.class.getClassLoader());
        indicator = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(device, flags);
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

    public TaskListResponse.device getDevice() {
        return device;
    }

    public void setDevice(TaskListResponse.device device) {
        this.device = device;
    }

    public  ScanDevice(){

    }

    public boolean isIndicator() {
        return indicator;
    }

    public void setIndicator(boolean indicator) {
        this.indicator = indicator;
    }
}
