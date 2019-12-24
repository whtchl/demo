package com.bluetron.core.model.data.repository.task;

import com.bluetron.core.bean.task.TaskListResponse;
import com.bluetron.core.model.data.datastore.task.TaskLocalDataStore;
import com.bluetron.core.model.data.datastore.task.TaskRemoteDataStore;

import java.util.List;

import io.reactivex.Observable;

/**
 * @auther tongxb
 * @data 2019-12-24
 */
public class TaskRepositoryImpl implements TaskRepository{


    TaskLocalDataStore localDataStore;
    TaskRemoteDataStore remoteDataStore;


    public TaskRepositoryImpl(TaskLocalDataStore localDataStore,TaskRemoteDataStore remoteDataStore){
        this.localDataStore = localDataStore;
        this.remoteDataStore = remoteDataStore;
    }

    @Override
    public Observable<List<TaskListResponse>> getTaskList(String token) {
        return remoteDataStore.getTaskList(token);
    }





}
