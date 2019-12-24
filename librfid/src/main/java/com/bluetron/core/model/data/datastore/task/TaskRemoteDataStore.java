package com.bluetron.core.model.data.datastore.task;

import com.bluetron.core.bean.task.TaskListResponse;
import com.bluetron.core.model.api.ApiService;

import io.reactivex.Observable;

/**
 * @auther tongxb
 * @data 2019-12-24
 */
public class TaskRemoteDataStore extends TaskDataStore{

    private ApiService mService;


    @Override
    public Observable<TaskListResponse> getTaskList(String token) {
        return mService.getTaskList(token);
    }

}
