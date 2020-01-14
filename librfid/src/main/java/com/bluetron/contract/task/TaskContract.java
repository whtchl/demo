package com.bluetron.contract.task;

import com.bluetron.base.presenter.BasePresenter;
import com.bluetron.base.view.BaseView;
import com.bluetron.core.bean.task.TaskListResponse;

import java.util.List;

/**
 * @auther tongxb
 * @data 2019-12-24
 */
public interface TaskContract {

    interface View extends BaseView{

        void onGetTaskListSuccess(List<TaskListResponse> response);
        void onGetTaskListErr(Throwable t);
        void onUploadTaskList();
    }


    interface Presenter extends BasePresenter<View>{

        void getTaskList();
        void uploadTaskList(TaskListResponse taskListResponse);
    }

}
