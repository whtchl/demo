package com.bluetron.router;

/**
 * @auther tongxb
 * @data 2019-12-24
 */
public class PathConstants {

    //scheme task
    private static final String PATH_GROUP_TASK = "/task";
    public static final String PATH_TASK_LIST = PATH_GROUP_TASK + "/taskList";
    public static final String PATH_TASK_DETAIL = PATH_GROUP_TASK + "/taskDetail";


    public static final String PATH_RFID = "/rfid";
    public static final String PATH_RFID_DEMO = PATH_RFID + "/rfiddemo";
    public static final String PATH_WRITE_RFID = PATH_RFID + "/writerfid";
    public static final String PATH_AROUND_RFID_LIST = PATH_RFID + "/aroundrfidlist";

    public static final String PATH_DEVICE = "/device";
    public static final String PATH_DEVICE_DETAIL = PATH_DEVICE + "/devicedetail";
    public static final String PATH_DEVICE_LIST = PATH_DEVICE + "/devicelist";
}
