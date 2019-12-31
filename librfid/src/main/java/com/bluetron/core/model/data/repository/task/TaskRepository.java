package com.bluetron.core.model.data.repository.task;

import com.bluetron.core.bean.task.TaskListResponse;
import com.bluetron.rxretrohttp.bean.NoneResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * @auther tongxb
 * @data 2019-12-24
 */
public interface TaskRepository {

    Observable<List<TaskListResponse>> getTaskList();
    Observable<NoneResponse> uploadTaskList(TaskListResponse taskListResponse);
}
