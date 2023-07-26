package com.example.oltest.controller;

import com.example.oltest.Mapper.OlMapper;
import com.example.oltest.pojo.Ol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ol")
public class OlController {
    @Autowired
    private OlMapper olMapper;
    @GetMapping("/all")
    public List<Ol> searchAll(){
        return olMapper.searchAll();
    }
}
