package com.example.oltest.Mapper;

import com.example.oltest.pojo.Ol;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OlMapper {
    @Select("SELECT * FROM oltest LIMIT 1")
    public List<Ol> searchAll();
}
