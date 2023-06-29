package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.SizeEntity;
import com.cybersoft.cozastore.payload.response.ProductResponse;
import com.cybersoft.cozastore.payload.response.SizeResponse;
import com.cybersoft.cozastore.repository.SizeRepository;
import com.cybersoft.cozastore.service.impl.ISizeService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class SizeService implements ISizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    Gson gson = new Gson();

    @Override
    public List<SizeResponse> getAllSize() {

        List<SizeResponse> responeList = new ArrayList<>();

        if(redisTemplate.hasKey("listSize")) {
            System.out.println("Redis có dữ liệu size");
            String dataSize = (String) redisTemplate.opsForValue().get("listSize");
            Type listType = new TypeToken<ArrayList<SizeResponse>>(){}.getType();
            responeList = new Gson().fromJson(dataSize,listType);
        } else {
            System.out.println("Redis không có dữ liệu size");
            List<SizeEntity> list = sizeRepository.findAll();
            for (SizeEntity size: list) {
                SizeResponse respone = new SizeResponse();
                respone.setId(size.getId());
                respone.setName(size.getName());
                responeList.add(respone);
            }

        }

        String dataSize = gson.toJson(responeList);
        // Lưu trữ dữ liệu lên redis thông qua redistemplate
        redisTemplate.opsForValue().set("listSize", dataSize);
        return responeList;
    }
}
