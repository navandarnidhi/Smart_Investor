package com.smartinvestor.service;

import java.util.List;

import com.smartinvestor.domain.OrderType;
import com.smartinvestor.model.Coin;
import com.smartinvestor.model.Order;
import com.smartinvestor.model.OrderItem;
import com.smartinvestor.model.User;
import com.smartinvestor.request.CreateOrderRequest;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId);

    List<Order> getAllOrdersForUser(Long userId, String orderType,String assetSymbol);

    void cancelOrder(Long orderId);

//    Order buyAsset(CreateOrderRequest req, Long userId, String jwt) throws Exception;

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;

//    Order sellAsset(CreateOrderRequest req,Long userId,String jwt) throws Exception;


}
