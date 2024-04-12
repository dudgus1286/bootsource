package com.example.mart.repository;

import java.util.List;

import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;

public interface QueryDslOrderRepository {
    public List<Object[]> joinList();

    public List<Member> members();

    public List<Item> items();
}
