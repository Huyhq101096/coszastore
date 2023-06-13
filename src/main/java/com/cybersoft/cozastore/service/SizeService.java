package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.SizeEntity;
import com.cybersoft.cozastore.payload.response.SizeResponse;
import com.cybersoft.cozastore.repository.SizeRepository;
import com.cybersoft.cozastore.service.impl.ISizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SizeService implements ISizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public List<SizeResponse> getAllSize() {
        List<SizeEntity> list = sizeRepository.findAll();
        List<SizeResponse> responeList = new ArrayList<>();
        for (SizeEntity size: list) {
            SizeResponse respone = new SizeResponse();
            respone.setId(size.getId());
            respone.setName(size.getName());
            responeList.add(respone);
        }
        System.out.println(list);
        return responeList;
    }
}
