package com.github.prakasit.springredisstrategies.controller;

import com.github.prakasit.springredisstrategies.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/{id}/view", method = RequestMethod.POST)
    public ResponseEntity incrementViewCount(@PathVariable("id") Long productId) {
        productService.incrementProductView(productId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}/viewCount", method = RequestMethod.GET)
    public ResponseEntity getProductViewCount(@PathVariable("id") Long productId) {
        return ResponseEntity.ok(productService.getProductViewCount(productId));

    }
}
