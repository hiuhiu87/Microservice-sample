package dev.nmh.productservice.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateProductRequest {

    @NotEmpty(message = "Product name is required")
    private String name;

    private String description;

    @NotNull(message = "Product price is required")
    private BigDecimal price;

}
