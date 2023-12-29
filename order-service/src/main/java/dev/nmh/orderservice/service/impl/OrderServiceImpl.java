package dev.nmh.orderservice.service.impl;

import dev.nmh.orderservice.entity.Order;
import dev.nmh.orderservice.entity.OrderLineItems;
import dev.nmh.orderservice.infrastructure.exception.ResponseModel;
import dev.nmh.orderservice.model.request.OrderLineItemsRequest;
import dev.nmh.orderservice.model.request.OrderRequest;
import dev.nmh.orderservice.model.response.InventoryResponse;
import dev.nmh.orderservice.repository.OrderRepository;
import dev.nmh.orderservice.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClient;

    @Override
    public ResponseModel createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest
                .getOrderLineItems()
                .stream()
                .map(this::mapOrderLineItems)
                .toList();
        order.setOrderLineItems(orderLineItems);

        List<String> skuCodes = orderLineItems
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        InventoryResponse[] inventoryResponses = webClient.build().get()
                .uri(
                        "http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build()
                )
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        assert inventoryResponses != null;
        boolean isInStock = Stream.of(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if (isInStock) {
            return new ResponseModel(
                    HttpStatus.BAD_REQUEST,
                    LocalDateTime.now(),
                    "Order failed",
                    "Inventory is not available",
                    null);
        } else {
            Order orderResponse = orderRepository.save(order);
            return new ResponseModel(HttpStatus.CREATED, LocalDateTime.now(), "Order created successfully", null, orderResponse);

        }
    }

    private OrderLineItems mapOrderLineItems(OrderLineItemsRequest orderLineItemsRequest) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setId(orderLineItemsRequest.getId());
        orderLineItems.setQuantity(orderLineItemsRequest.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsRequest.getSkuCode());
        orderLineItems.setPrice(orderLineItemsRequest.getPrice());
        return orderLineItems;
    }

}

