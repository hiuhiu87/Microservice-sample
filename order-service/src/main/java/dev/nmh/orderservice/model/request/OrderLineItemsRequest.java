package dev.nmh.orderservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsRequest {

    private Long id;

    private String skuCode;

    private BigDecimal price;

    private Integer quantity;

}
