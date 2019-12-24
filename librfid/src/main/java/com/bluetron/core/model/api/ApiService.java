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


//    @GET(Apis.LoginAPI.PHONE_LOGIN)
//    Observable<TaskListResponse> getToken(@Query("token")String token);


    @GET(Apis.TaskAPI.GET_TASK_LIST)
    Observable<TaskListResponse> getTaskList(@Query("token")String token);

}
