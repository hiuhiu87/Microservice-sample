package dev.nmh.orderservice.service;

import dev.nmh.orderservice.infrastructure.exception.ResponseModel;
import dev.nmh.orderservice.model.request.OrderRequest;

public interface OrderService {

    ResponseModel createOrder(OrderRequest orderRequest);

}
