package com.example.oltest.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class Ol {
    private int id;
    private String name;
    private Timestamp createtime;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    public Timestamp getCreatetime(){
        return createtime;
    }
    public void setCreatetime(Timestamp createtime){
        this.createtime = createtime;
    }
}
