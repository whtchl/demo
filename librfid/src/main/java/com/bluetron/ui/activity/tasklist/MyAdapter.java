package com.bluetron.ui.activity.tasklist;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluetron.core.bean.task.TaskListResponse;
import com.bluetron.librfid.R;
import com.bluetron.router.Navigation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijianchang@yy.com on 2017/4/12.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TaskListResponse> datas;
    private Context context;
    private int normalType = 0;
    private int footType = 1;
    private boolean hasMore = true;
    private boolean fadeTips = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public MyAdapter(List<TaskListResponse> datas, Context context, boolean hasMore) {
        this.datas = datas;
        this.context = context;
        this.hasMore = hasMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == normalType) {
            return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.item_task, null));
        } else {
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.footview, null));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalHolder) {
            ((NormalHolder) holder).itemTaskName.setText(datas.get(position).getName());
            //((NormalHolder) holder).itemTaskTime.setText(datas.get(position).getCreateTime());
            ((NormalHolder) holder).itemTaskNumber.setText(datas.get(position).getList().size() + "");

            /*((NormalHolder) holder).llItemTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.navigateToTaskDetail();
                }
            });*/
            ((NormalHolder) holder).llItemTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.navigateToTaskDetail(datas.get(position));//Navigation.navigateToTaskDetail(datas.get(position));
                }
            });

        } else {
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            if (hasMore == true) {
                fadeTips = false;
                if (datas.size() > 0) {
                    ((FootHolder) holder).tips.setText("");//正在加载更多...
                }
            } else {
                if (datas.size() > 0) {
                    ((FootHolder) holder).tips.setText("");//没有更多数据了
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((FootHolder) holder).tips.setVisibility(View.GONE);
                            fadeTips = true;
                            hasMore = true;
                        }
                    }, 500);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    public int getRealLastPosition() {
        return datas.size();
    }


    public void updateList(List<TaskListResponse> newDatas, boolean hasMore) {
        if (newDatas != null) {
            datas.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }

    class NormalHolder extends RecyclerView.ViewHolder {
        private TextView itemTaskName;
        private TextView itemTaskTime;
        private TextView itemTaskNumber;
        private LinearLayout llItemTask;

        public NormalHolder(View itemView) {
            super(itemView);
            itemTaskName = (TextView) itemView.findViewById(R.id.tv_item_task_name);
            itemTaskTime = (TextView) itemView.findViewById(R.id.tv_item_task_time);
            itemTaskNumber = (TextView) itemView.findViewById(R.id.tv_item_task_number);
            llItemTask = (LinearLayout) itemView.findViewById(R.id.ll_item_task);


        }
    }

    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = (TextView) itemView.findViewById(R.id.tips);
        }
    }

    public boolean isFadeTips() {
        return fadeTips;
    }

    public void resetDatas() {
        datas = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }
}
