package com.bluetron.core.bean.task;

import java.util.List;

/**
 * @auther tongxb
 * @data 2019-12-24
 */
public class TaskListResponse {

    private String id;
    private String name;
    private long createTime;
    private int taskNum;
    private List<device> list;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }

    public List<device> getList() {
        return list;
    }

    public void setList(List<device> list) {
        this.list = list;
    }

    class device {
        private String id;
        private String lastModifyDate;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLastModifyDate() {
            return lastModifyDate;
        }

        public void setLastModifyDate(String lastModifyDate) {
            this.lastModifyDate = lastModifyDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }




}
