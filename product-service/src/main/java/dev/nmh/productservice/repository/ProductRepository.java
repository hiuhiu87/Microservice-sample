package dev.nmh.productservice.repository;

import dev.nmh.productservice.entity.Product;
import dev.nmh.productservice.model.response.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(
            value = """
            SELECT p.product_id as id,
                   p.product_name as name,
                   p.product_description as description,
                   p.product_price as price
            FROM product p
            """,
            nativeQuery = true
    )
    List<ProductResponse> getAllProducts();

}
