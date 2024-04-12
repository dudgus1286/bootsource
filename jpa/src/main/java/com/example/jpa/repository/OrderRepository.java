package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jpa.entity.Address;
import com.example.jpa.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 주소 조회
    @Query("select o.homeAddress from Order o")
    List<Address> findByHomeAddress(); // Order 객체의 해당 필드 데이터 타입이 Address 로 되어있어서

    @Query("select o.member2, o.product, o.id from Order o ")
    List<Object[]> findByOrders();

}
