package com.example.oltest.service;

import com.example.oltest.exception.DataNotFoundException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class CacheService {
    private final LoadingCache<String, String> loadingCache;

    public CacheService() {
        this.loadingCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .refreshAfterWrite(1, TimeUnit.MINUTES)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build(new CacheLoader<String, String>() {
                    public String load(String key) throws DataNotFoundException {
                        throw new DataNotFoundException("No data found for key " + key);
                    }
                });
    }

    public String getStatusByUidAndBusiness(String key) throws ExecutionException {
        try {
            return loadingCache.get(key);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof DataNotFoundException) {
                return null;
            } else {
                throw e;
            }
        }
    }

    public void putStatusByUidAndBusiness(String key, String value) {
        loadingCache.put(key, value);
    }
}
