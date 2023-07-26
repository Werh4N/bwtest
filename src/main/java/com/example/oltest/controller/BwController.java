package com.example.oltest.controller;

import com.example.oltest.Mapper.BwMapper;
import com.example.oltest.pojo.Bw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bw")
public class BwController {
    @Autowired
    private BwMapper bwMapper;
    @GetMapping("/all")
    public List<Bw> searchAll(){
        return bwMapper.searchAll();
    }

    @GetMapping("/status/{id}")
    public String getStatusById(@PathVariable int id) {
        return bwMapper.getStatusById(id);
    }
    @GetMapping("/uid")
    public String getStatusByUidAndBusiness(@RequestParam int uid, @RequestParam String business) {
        return bwMapper.getStatusByUidAndBusiness(uid, business);
    }
}
