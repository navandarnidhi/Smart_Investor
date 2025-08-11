package com.smartinvestor.request;

import lombok.Data;

import java.math.BigDecimal;

import com.smartinvestor.domain.OrderType;
import com.smartinvestor.model.Coin;


@Data
public class CreateOrderRequest {
    private String coinId;
    private double quantity;
    private OrderType orderType;
}
