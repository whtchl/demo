package com.bluetron.core.model.api;

import com.bluetron.core.bean.task.TaskListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @auther tongxb
 * @data 2019-12-24
 */
public interface ApiService {


    @GET(Apis.TaskAPI.GET_TASK_LIST)
    Observable<TaskListResponse> getToken(@Query("token")String token);

}
