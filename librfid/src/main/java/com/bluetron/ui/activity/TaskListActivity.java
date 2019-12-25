package com.bluetron.ui.activity;

import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bluetron.base.activity.BaseBackActivity;
import com.bluetron.base.activity.BaseTitleBackActivity;
import com.bluetron.contract.task.TaskContract;
import com.bluetron.core.bean.task.TaskListResponse;
import com.bluetron.librfid.R;
import com.bluetron.presenter.task.TaskPresenter;
import com.bluetron.router.PathConstants;

import java.util.List;

/**
 * @auther tongxb
 * @data 2019-12-24
 */
@Route(path = PathConstants.PATH_TASK_LIST)
public class TaskListActivity extends BaseTitleBackActivity  implements TaskContract.View{
    TaskPresenter taskPresenter;
    @Override
    protected void initViews() {
        inflateBaseView();
        setBackVisibility(View.VISIBLE);

        setTitleTxt("所有设备");

    }


    @Override
    protected void initVariables() {

    }

    @Override
    protected int getContentViewLayoutID() {

        return R.layout.task_list_activity;
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskPresenter = new TaskPresenter(this);
        taskPresenter.getTaskList();
    }
    @Override
    public void onGetTaskList(List<TaskListResponse> response) {
        Log.i("tchl","TaskListActivity size:"+response.size());
    }
}
