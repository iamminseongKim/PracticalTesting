package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;

import java.util.List;
import java.util.stream.Collectors;


/**
 * readOnly = true : 읽기 전용
 * CRUD에서 CUD가 동작을 안함. /only read
 * JPA : CUD 스냅샷 저장, 변경감지 X → 성능 향상
 * <p>
 * CQRS - Command / Query
 */
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductNumberFactory productNumberFactory;

    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        // productNumber ("001", "002"...)
        // DB에서 마지막 저장된 Product의 상품번호 읽어와서 + 1
        String nextProductNumber = productNumberFactory.createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);

    }



    public List<ProductResponse> getSellingProducts() {

        List<Product> productList = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return productList.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());

    }
}
