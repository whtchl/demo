package com.bluetron.core.model.api;

/**
 * @auther tongxb
 * @data 2019-12-24
 */
public class Apis {

    private static final String API_REMOTE_TITLE = "/";



    public static class LoginAPI {
        public static final String
                PHONE_LOGIN = API_REMOTE_TITLE + "user/login",
                LOGOUT = API_REMOTE_TITLE + "user/logout";
    }


    public static class TaskAPI {
        public static final String
                GET_TASK_LIST = API_REMOTE_TITLE + "tasks",
                POST_TASK_LIST = API_REMOTE_TITLE + "task";
    }
}
