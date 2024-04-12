package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.jpa.entity.Member2;
import com.example.jpa.entity.Product;
import com.example.jpa.entity.QMember2;
import com.example.jpa.entity.QProduct;
import com.querydsl.core.BooleanBuilder;

@SpringBootTest
public class QuerydslTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Member2Repository member2Repository;

    @Test
    public void testQueryDsl() {
        // QProduct 가져오기
        QProduct product = QProduct.product;

        Iterable<Product> products = productRepository.findAll(product.name.eq("제품1"));

        // 제품명이 제품1 이고 가격이 500 초과인 제품 조회
        // products =
        // productRepository.findAll(product.name.eq("제품1").and(product.price.gt(500)));

        // 제품명이 제품1 이고 가격이 500 이상인 제품 조회
        products = productRepository.findAll(product.name.eq("제품1").and(product.price.goe(500)));
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(product.name.eq("제품2"));
        builder.and(product.price.goe(1100));

        products = productRepository.findAll(builder);

        // 제품명에 제품이란 글자가 포함된 상품 조회
        products = productRepository.findAll(product.name.startsWith("제품"));
        // products = productRepository.findAll(product.name.endsWith("제품"));
        for (Product pro : products) {
            System.out.println(pro);
        }
    }

    @Test
    public void testQueryDsl2() {
        QMember2 member2 = QMember2.member2;

        BooleanBuilder builder = new BooleanBuilder(); // 조건
        builder.and(member2.userName.eq("User1"));

        Iterable<Member2> list = member2Repository.findAll(builder, Sort.by("id").descending());
        for (Member2 mem : list) {
            System.out.println(mem);
        }
    }

}
