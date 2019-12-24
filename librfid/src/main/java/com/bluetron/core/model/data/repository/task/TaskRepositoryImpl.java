package com.bluetron.core.model.data.repository.task;

import com.bluetron.core.bean.task.TaskListResponse;
import com.bluetron.core.model.data.datastore.task.TaskLocalDataStore;
import com.bluetron.core.model.data.datastore.task.TaskRemoteDataStore;

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
    public Observable<TaskListResponse> getTaskList(String token) {
        return null;
    }


    public Observable<TaskListResponse> getTaskList(){
        return null;
    }


}
