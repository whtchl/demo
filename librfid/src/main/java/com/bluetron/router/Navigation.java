package com.bluetron.router;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bluetron.core.bean.task.TaskListResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther tongxb
 * @data 2019-12-24
 */
public class Navigation {


    //task list
    public static void navigateToTaskList() {
        ARouter.getInstance().build(PathConstants.PATH_TASK_LIST)
                .navigation();
    }

    public static void navigateToRfidDemo() {
        ARouter.getInstance().build(PathConstants.PATH_RFID_DEMO)
                .navigation();
    }

    //task detail
    public static void navigateToTaskDetail(TaskListResponse taskListResponse) {//, ArrayList<TaskListResponse.device> deviceList
        ARouter.getInstance().build(PathConstants.PATH_TASK_DETAIL)
                .withParcelable("taskListResponse",  taskListResponse)
                //.withParcelableArrayList("deviceList",deviceList)
                .navigation();
    }

    public static void navigateToAroundRfidList() {//, ArrayList<TaskListResponse.device> deviceList
        ARouter.getInstance().build(PathConstants.PATH_AROUND_RFID_LIST)
                .navigation();
    }

    public static void navigateDeviceDetail() {//, ArrayList<TaskListResponse.device> deviceList
        ARouter.getInstance().build(PathConstants.PATH_DEVICE_DETAIL)
                .navigation();
    }


    public static void navigateDeviceList() {//, ArrayList<TaskListResponse.device> deviceList
        ARouter.getInstance().build(PathConstants.PATH_DEVICE_LIST)
                .navigation();
    }

}
