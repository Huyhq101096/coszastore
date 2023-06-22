package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.CountryEntity;
import com.cybersoft.cozastore.entity.OrderDetailEntity;
import com.cybersoft.cozastore.entity.OrderEntity;
import com.cybersoft.cozastore.entity.UserEntity;
import com.cybersoft.cozastore.entity.ids.OrderDetailIds;
import com.cybersoft.cozastore.exception.UserNotFoundException;
import com.cybersoft.cozastore.payload.request.OrderProductRequest;
import com.cybersoft.cozastore.payload.request.OrderRequest;
import com.cybersoft.cozastore.repository.OrderDetailRepository;
import com.cybersoft.cozastore.repository.OrderRepository;
import com.cybersoft.cozastore.repository.UserRepository;
import com.cybersoft.cozastore.service.impl.IOrderService;
import com.cybersoft.cozastore.utils.JWTHelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTHelperUtils jwtHelperUtils;


    @Override
    public boolean addOrder(OrderRequest orderRequest, HttpServletRequest request) {
        /*
        * B1: Lấy token
        * B2: Giải mã lại token
        * B3: Username hoặc Userid
        *
        * */

        // Lấy token từ header
        String header = request.getHeader("Authorization");
        if(header == null || header.isEmpty()) {
            // Lỗi có thể biết được
            throw new UserNotFoundException(500, "You don't allow access this feature");
        }


        String token = header.substring(7);
        // Kiểm tra token lấy được xem có thể do hệ thống sinh ra hay không.
        String dataToken = jwtHelperUtils.validateToken(token);
        // query database và lấy ra id

        UserEntity userEntity = userRepository.findByUsername(dataToken);
        if(userEntity == null ) {
            throw new UserNotFoundException(500, "Username do not exist");
        }

        CountryEntity country = new CountryEntity();
        country.setId(orderRequest.getCountryId());


        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCountry(country);
        orderEntity.setUser(userEntity);

        orderRepository.save(orderEntity);

        // Them du lieu cho ban order detail
        for (OrderProductRequest data: orderRequest.getListProduct()) {

            OrderDetailIds ids = new OrderDetailIds();
            ids.setOrderId(orderEntity.getId());
            ids.setProductId(data.getId());

            OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
            orderDetailEntity.setIds(ids);
            orderDetailEntity.setPrice(data.getPrice());
            orderDetailEntity.setQuantity(data.getQuantity());
            orderDetailRepository.save(orderDetailEntity);
        }

        return false;
    }
}
