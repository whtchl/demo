package com.bluetron.presenter.task;

import com.bluetron.base.presenter.BasePresenter;
import com.bluetron.base.presenter.BasePresenterImpl;
import com.bluetron.contract.task.TaskContract;
import com.bluetron.core.bean.task.TaskListResponse;
import com.bluetron.core.model.data.datastore.task.TaskLocalDataStore;
import com.bluetron.core.model.data.datastore.task.TaskRemoteDataStore;
import com.bluetron.core.model.data.repository.task.TaskRepository;
import com.bluetron.core.model.data.repository.task.TaskRepositoryImpl;
import com.bluetron.rxretrohttp.subscriber.ApiObserver;

import java.util.List;

/**
 * @auther tongxb
 * @data 2019-12-24
 */
public class TaskPresenter extends BasePresenterImpl<TaskContract.View> implements TaskContract.Presenter {

    TaskRepository mRepository;
    TaskContract.View mView;


    public TaskPresenter(TaskContract.View mView){
        this.mView = mView;
        mRepository = new TaskRepositoryImpl(new TaskLocalDataStore(),new TaskRemoteDataStore());
    }


    @Override
    public void getTaskList() {

        mRepository.getTaskList()
                .compose(mView.getLifecycleTransformer())
                .subscribe(new ApiObserver<List<TaskListResponse>>(mView) {
                    @Override
                    protected void success(List<TaskListResponse> data) {
                        mView.onGetTaskList(data);
                    }
                });
    }
}
