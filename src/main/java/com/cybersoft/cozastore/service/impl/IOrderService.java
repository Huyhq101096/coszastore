package com.cybersoft.cozastore.service.impl;

import com.cybersoft.cozastore.payload.request.OrderRequest;

import javax.servlet.http.HttpServletRequest;

public interface IOrderService {

    boolean addOrder(OrderRequest orderRequest, HttpServletRequest request);


}
