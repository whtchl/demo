package com.bluetron.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bluetron.contract.task.TaskContract;
import com.bluetron.core.bean.task.TaskListResponse;
import com.bluetron.librfid.R;

import java.util.List;

public class TaskDetailActivity extends AppCompatActivity implements TaskContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
    }

    @Override
    public void onGetTaskList(List<TaskListResponse> response) {

    }
}
