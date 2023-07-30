package com.example.oltest.service;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
public class BloomFilterService {
    private final BloomFilter<String> filter;

    public BloomFilterService() {
        this.filter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), 100000, 0.01);
    }

    public void put(String value) {
        filter.put(value);
    }

    public boolean mightContain(String value) {
        return filter.mightContain(value);
    }
}
