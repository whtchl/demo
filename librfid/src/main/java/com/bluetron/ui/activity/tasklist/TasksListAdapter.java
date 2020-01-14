package com.bluetron.ui.activity.tasklist;

import android.support.annotation.Nullable;
import android.view.View;

import com.bluetron.core.bean.task.TaskListResponse;
import com.bluetron.librfid.R;
import com.bluetron.router.Navigation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class TasksListAdapter extends BaseQuickAdapter<TaskListResponse, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {
    public TasksListAdapter() {
        super(R.layout.item_task);
        setOnItemClickListener(this);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskListResponse item) {
        helper.setText(R.id.tv_item_task_name,item.getName());
        helper.setText(R.id.tv_item_task_number,item.getList().size()+"");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Navigation.navigateToTaskDetail(getData().get(position));
    }
}
