package com.bluetron.core.model.data.datastore.task;

import com.bluetron.core.bean.task.TaskListResponse;
import com.bluetron.core.model.api.ApiService;
import com.bluetron.rxretrohttp.RxRetroHttp;

import java.util.List;

import io.reactivex.Observable;

/**
 * @auther tongxb
 * @data 2019-12-24
 */
public class TaskRemoteDataStore extends TaskDataStore{

    private ApiService mService;

    public TaskRemoteDataStore(){
        mService = RxRetroHttp.create(ApiService.class);
    }


    @Override
    public Observable<List<TaskListResponse>> getTaskList(String token) {
        return mService.getTaskList(token);
    }

}
