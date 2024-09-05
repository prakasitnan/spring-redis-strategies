package com.github.prakasit.springredisstrategies.service;

import com.github.prakasit.springredisstrategies.model.db.ProductEntity;
import com.github.prakasit.springredisstrategies.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY_PREFIX = "product:viewCount:";

    public ProductService(ProductRepository productRepository, RedisTemplate<String, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
    }

    /*
     * Write-through caching
     */
    public void incrementProductView(Long productId) {
        String cacheKey = CACHE_KEY_PREFIX + productId;

        redisTemplate.opsForValue().increment(cacheKey, 1);

        redisTemplate.expire(cacheKey, 1, TimeUnit.DAYS);
    }

    public Long getProductViewCount(Long productId) {
        String cacheKey = CACHE_KEY_PREFIX + productId;

        Integer viewCount = (Integer) redisTemplate.opsForValue().get(cacheKey);
        return viewCount != null ? viewCount : 0L;
    }

    /*
     * Write-Back Caching
     */
    @Scheduled(fixedRate = 60 * 1000 * 1000)
    public void flushViewsToDatabase() {
        log.info("flushViewsToDatabase : "+ new Timestamp(System.currentTimeMillis()));
        List<Long> productIds = productRepository.findAllProductIds();

        for (Long productId : productIds) {
            String cacheKey = CACHE_KEY_PREFIX + productId;
            Integer cacheViewCount = (Integer) redisTemplate.opsForValue().get(cacheKey);

            if (cacheViewCount != null && cacheViewCount > 0) {
                Optional<ProductEntity> productOptional = productRepository.findById(productId);
                if (productOptional.isPresent()) {
                    ProductEntity product = productOptional.get();
                    Long totalViews = product.getViewCount() + cacheViewCount;

                    product.setViewCount(totalViews);
                    productRepository.save(product);

                    redisTemplate.delete(cacheKey);
                }
            }
        }

    }
}
