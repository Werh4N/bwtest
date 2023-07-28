package com.example.oltest.pojo;


public class Bw {
    private int id;
    private int uid;
    private String status;
    private String business;
    private int create_time;
    private int update_time;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public void setCreateTime(int createTime) {
        this.create_time = createTime;
    }

    public int getCreateTime() {
        return create_time;
    }

    public void setUpdateTime(int updateTime) {
        this.update_time = updateTime;
    }

    public int getUpdateTime() {
        return update_time;
    }
}
