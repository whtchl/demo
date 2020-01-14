package com.bluetron.ui.activity.tasklist;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bluetron.base.activity.BaseTitleBackActivity;
import com.bluetron.base.activity.BaseTitleBackNoLoadingDialogActivity;
import com.bluetron.contract.task.TaskContract;
import com.bluetron.core.bean.task.TaskListResponse;
import com.bluetron.librfid.R;
import com.bluetron.presenter.task.TaskPresenter;
import com.bluetron.router.Navigation;
import com.bluetron.router.PathConstants;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.List;

@Route(path = PathConstants.PATH_TASK_LIST)
public class TasksListActivity extends BaseTitleBackNoLoadingDialogActivity implements TaskContract.View, SwipeRefreshLayout.OnRefreshListener {
    TaskPresenter taskPresenter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TasksListAdapter mAdapter;
    @Override
    protected void initViews() {
        taskPresenter = new TaskPresenter(this);
        inflateBaseView();
        setBackVisibility(View.VISIBLE);
        setTitleTxt("任务列表");

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        initRefreshLayout();
        mSwipeRefreshLayout.setRefreshing(true);
        refresh();
    }

    private void initAdapter() {
        mAdapter = new TasksListAdapter();
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        },mRecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);
        /*mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });*/
        /*mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                Toast.makeText(TasksListActivity.this, Integer.toString(position), Toast.LENGTH_LONG).show();

                Navigation.navigateToTaskDetail(mAdapter.getData().get(position));
            }
        });*/
    }

    private void loadMore() {
        Toast.makeText(this,"load more: no data for loadmore",Toast.LENGTH_LONG ).show();
    }

    private void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }
    @Override
    protected void initVariables() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return  R.layout.activity_tasks_list;
    }

    @Override
    public void onGetTaskListSuccess(List<TaskListResponse> response) {
        setData(true, response);
        mAdapter.setEnableLoadMore(true);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetTaskListErr(Throwable t) {
        Toast.makeText(this,"获取数据失败", Toast.LENGTH_LONG).show();
        mAdapter.setEnableLoadMore(true);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onUploadTaskList() {

    }

    private void setData(boolean isRefresh, List<TaskListResponse> data){
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        mAdapter.loadMoreEnd(isRefresh);
    }

    private void refresh() {
        mAdapter.setEnableLoadMore(false);
        taskPresenter.getTaskList();
    }

    @Override
    public void onRefresh() {
        refresh();
    }
}
