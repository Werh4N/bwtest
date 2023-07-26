package com.example.oltest.Mapper;

import com.example.oltest.pojo.Bw;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BwMapper {
    @Select("SELECT * FROM bwtest LIMIT 10")
    public List<Bw> searchAll();
    @Select("SELECT status FROM bwtest WHERE id = #{id}")
    public String getStatusById(int id);

    @Select("SELECT status FROM bwtest WHERE uid = #{uid} AND business = #{business}")
    public String getStatusByUidAndBusiness(int uid, String business);
}
