package com.bluetron.ui.activity.devicelist;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bluetron.base.activity.BaseTitleBackActivity;
import com.bluetron.contract.task.TaskContract;
import com.bluetron.core.bean.task.TaskListResponse;
import com.bluetron.librfid.R;
import com.bluetron.presenter.task.TaskPresenter;
import com.bluetron.router.PathConstants;

import java.util.ArrayList;
import java.util.List;

@Route(path = PathConstants.PATH_DEVICE_LIST)
public class DeviceListActivity extends BaseTitleBackActivity implements TaskContract.View, SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 9999;
    private GridLayoutManager mLayoutManager;
    private MyDeviceListAdapter adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private List<TaskListResponse> taskListResponses = new ArrayList<TaskListResponse>();
    TaskPresenter taskPresenter;
    @Override
    protected void initViews() {
        taskPresenter = new TaskPresenter(this);
        inflateBaseView();
        setBackVisibility(View.VISIBLE);
        setTitleTxt("所有设备");

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        initRefreshLayout();
        initRecyclerView();
    }

    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);
    }

    private List<TaskListResponse> getDatas(final int firstIndex, final int lastIndex) {
        List<TaskListResponse> resList = new ArrayList<TaskListResponse>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < taskListResponses.size()) {
                resList.add(taskListResponses.get(i));
            }
        }
        return resList;
    }


    private void initRecyclerView() {
        adapter = new MyDeviceListAdapter(setDeviceList(getDatas(0, PAGE_COUNT)), this, getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
        mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (adapter.isFadeTips() == false && lastVisibleItem + 1 == adapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (adapter.isFadeTips() == true && lastVisibleItem + 2 == adapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void updateRecyclerView(int fromIndex, int toIndex) {
        List<TaskListResponse> newDatas = getDatas(fromIndex, toIndex);
        adapter.updateList(setDeviceList(newDatas),true);
        /*if (newDatas.size() > 0) {
            adapter.updateList(newDatas, true);
        } else {
            adapter.updateList(null, false);
        }*/

    }

    private List<TaskListResponse.device> setDeviceList(List<TaskListResponse> taskListResponses){
        List<TaskListResponse.device> devices = new ArrayList<TaskListResponse.device>();
        for(int i=0;i<taskListResponses.size();i++){
            devices.addAll(taskListResponses.get(i).getList());
        }
        return devices;
    }
    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh(){
        refreshLayout.setRefreshing(false);
        taskPresenter.getTaskList();

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_device_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
    @Override
    public void onGetTaskListSuccess(List<TaskListResponse> response) {
        Log.i("tchl", "TaskListActivity size:" + response.size());
        taskListResponses = response;
        /*TaskListResponse response1 = response.get(0);
        for(int i =0; i<2;i++){
            taskListResponses.add(response1);
        }*/
        adapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1000);

    }


    @Override
    public void onGetTaskListErr(Throwable t) {

    }

    @Override
    public void onUploadTaskList() {

    }
}
