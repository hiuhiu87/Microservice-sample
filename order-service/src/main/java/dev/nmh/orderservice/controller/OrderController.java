package dev.nmh.orderservice.controller;

import dev.nmh.orderservice.infrastructure.exception.ResponseModel;
import dev.nmh.orderservice.model.request.OrderRequest;
import dev.nmh.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    @CircuitBreaker(name = "inventory-service", fallbackMethod = "createOrderFallback")
    @TimeLimiter(name = "inventory-service")
    @Retry(name = "inventory-service")
    public CompletableFuture<?> createOrder(@RequestBody OrderRequest orderRequest) {
        ResponseModel responseModel = orderService.createOrder(orderRequest);
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(responseModel, HttpStatus.OK));
    }

    public CompletableFuture<?> createOrderFallback(OrderRequest orderRequest, Throwable throwable) {
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
