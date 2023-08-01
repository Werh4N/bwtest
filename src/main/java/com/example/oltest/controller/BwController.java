package com.example.oltest.controller;

import com.example.oltest.Mapper.BwMapper;
import com.example.oltest.pojo.Bw;
import com.example.oltest.service.BloomFilterService;
import com.example.oltest.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/bw")
public class BwController {
    @Autowired
    private BwMapper bwMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private BloomFilterService bloomFilterService;
    @Autowired
    private CacheService cacheService;
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

    @PostMapping("/uid")
    public String getStatusByUidAndBusiness(@RequestBody Map<String, String> params) throws ExecutionException {
        int uid = Integer.parseInt(params.get("uid"));
        String business = params.get("business");
        String key = uid + ":" + business;

        String status = cacheService.getStatusByUidAndBusiness(key);

        if (status != null) {
            System.out.println("数据来自 LoadingCache");
            return status;
        }

        if (bloomFilterService.mightContain(key)) {
            System.out.println("布隆过滤器: 可能包含 " + key);
            if (redisTemplate.hasKey(key)) {
                System.out.println("数据来自 Redis");
                return redisTemplate.opsForValue().get(key);
            } else {
                System.out.println("Redis中不存在，转而从MySQL中搜索");
            }
        } else {
            System.out.println("布隆过滤器: 不包含 " + key);
        }

        status = bwMapper.getStatusByUidAndBusiness(uid, business);
        if (status != null) {
            System.out.println("数据来自 MySQL");
            redisTemplate.opsForValue().set(key, status, 300, TimeUnit.SECONDS);
            System.out.println("已添加 " + key + " 到redis");
            bloomFilterService.put(key);
            System.out.println("已添加 " + key + " 到布隆过滤器");
            cacheService.putStatusByUidAndBusiness(key, status);
            System.out.println("已添加 " + key + " 到LoadingCache");
        } else {
            System.out.println("在 MySQL 中未找到数据");
            status = "未找到对应数据";
        }
        return status;
    }

}
