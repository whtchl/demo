package com.bluetron.core.model.api;

import com.bluetron.rxretrohttp.bean.NoneResponse;
import com.bluetron.core.bean.task.TaskListResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @auther tongxb
 * @data 2019-12-24
 */
public interface ApiService {


//    @GET(Apis.LoginAPI.PHONE_LOGIN)
//    Observable<TaskListResponse> getToken(@Query("token")String token);


    @GET(Apis.TaskAPI.GET_TASK_LIST)
    Observable<List<TaskListResponse>> getTaskList();//@Query("token")String token

    @POST(Apis.TaskAPI.POST_TASK_LIST)
    Observable<NoneResponse> uploadTaskList(@Body TaskListResponse taskListResponse);//@Query("token")String token
}
