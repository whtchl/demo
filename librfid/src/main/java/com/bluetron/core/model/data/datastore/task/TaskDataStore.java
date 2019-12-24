package com.bluetron.core.model.data.datastore.task;

import com.bluetron.core.bean.task.TaskListResponse;

import io.reactivex.Observable;

/**
 * @auther tongxb
 * @data 2019-12-24
 */
public abstract class TaskDataStore {

    public Observable<TaskListResponse> getTaskList(String token) {
        return null;
    }

    public void saveTaskList(TaskListResponse taskListResponse) {

    }

}
