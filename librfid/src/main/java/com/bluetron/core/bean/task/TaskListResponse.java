package com.bluetron.core.bean.task;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther tongxb
 * @data 2019-12-24
 */
public class TaskListResponse implements Parcelable {

    private String id;
    private String name;
    private long createTime;
    private int taskNum;
    private ArrayList<device> list;
    //private static final long serialVersionUID = 8711368826010014025L;
/*
    protected TaskListResponse(Parcel in) {
        id = in.readString();
        name = in.readString();
        createTime = in.readLong();
        taskNum = in.readInt();
    }

    public static final Creator<TaskListResponse> CREATOR = new Creator<TaskListResponse>() {
        @Override
        public TaskListResponse createFromParcel(Parcel in) {
            return new TaskListResponse(in);
        }

        @Override
        public TaskListResponse[] newArray(int size) {
            return new TaskListResponse[size];
        }
    };*/

    protected TaskListResponse(Parcel in) {
        id = in.readString();
        name = in.readString();
        createTime = in.readLong();
        taskNum = in.readInt();
        list = in.createTypedArrayList(device.CREATOR);
    }

    public static final Creator<TaskListResponse> CREATOR = new Creator<TaskListResponse>() {
        @Override
        public TaskListResponse createFromParcel(Parcel in) {
            return new TaskListResponse(in);
        }

        @Override
        public TaskListResponse[] newArray(int size) {
            return new TaskListResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }

    public ArrayList<device> getList() {
        return list;
    }

    public void setList(ArrayList<device> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeLong(createTime);
        parcel.writeInt(taskNum);
        parcel.writeTypedList(list);
    }

/*    @Override
    public int describeContents() {
        return 0;
    }*/

/*    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeLong(createTime);
        parcel.writeInt(taskNum);
    }*/

    public static class device   implements Parcelable {
        private String id;
        private long lastModifyDate;
        private String name;
        public device(String id1,long lastModifyDate1 ,String name1) {
            this.id = id1;
            this.lastModifyDate = lastModifyDate1;
            this.name = name1;
        }

        protected device(Parcel in) {
            id = in.readString();
            lastModifyDate = in.readLong();
            name = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeLong(lastModifyDate);
            dest.writeString(name);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<device> CREATOR = new Creator<device>() {
            @Override
            public device createFromParcel(Parcel in) {
                return new device(in);
            }

            @Override
            public device[] newArray(int size) {
                return new device[size];
            }
        };

        public long getLastModifyDate() {
            return lastModifyDate;
        }

        public void setLastModifyDate(long lastModifyDate) {
            this.lastModifyDate = lastModifyDate;
        }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }




}
