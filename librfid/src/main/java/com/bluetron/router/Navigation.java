package com.bluetron.router;

import com.alibaba.android.arouter.launcher.ARouter;

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


    //task detail
    public static void navigateToTaskDetail() {
        ARouter.getInstance().build(PathConstants.PATH_TASK_DETAIL)
                .navigation();
    }

}
