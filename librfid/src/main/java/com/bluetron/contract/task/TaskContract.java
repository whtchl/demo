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

        void onGetTaskList(List<TaskListResponse> response);

    }


    interface Presenter extends BasePresenter<View>{

        void getTaskList(String token);

    }

}
