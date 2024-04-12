package com.example.mart.repository;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.QItem;
import com.example.mart.entity.QMember;
import com.example.mart.entity.QOrder;
import com.example.mart.entity.QOrderItem;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;

public class QueryDslOrderRepositoryImpl extends QuerydslRepositorySupport implements QueryDslOrderRepository {

    public QueryDslOrderRepositoryImpl() {
        super(Order.class);

    }

    @Override
    public List<Object[]> joinList() {
        // Q객체 가져오기
        QOrder order = QOrder.order;
        QMember member = QMember.member;
        QOrderItem orderItem = QOrderItem.orderItem;

        // 쿼리문 생성 1. JPAQuery 2. JPQLQuery
        // sql: select * from order o join member m on o.member_id = m.member_id;
        JPQLQuery<Order> query = from(order);
        // from order o

        // join : innerJoin, leftJoin, rightJoin, fullJoin 지원
        // join(조인 대상, 별칭으로 사용할 쿼리 타입)

        // 내부 조인 : join() , innerJoin()
        // query.innerJoin(order.member, member); // Order 객체의 멤버 필드 , Member 객체
        // query.join(order.member, member); // 위의 innerJoin(~) 과 동일
        // 외부 조인
        query.join(order.member, member).leftJoin(order.orderItems, orderItem);

        // join member m
        JPQLQuery<Tuple> tuple = query.select(order, member, orderItem); // 객체지향쿼리의 select o, t 와 같은 개념
        // on o.member_id = m.member_id
        List<Tuple> result = tuple.fetch(); // 데이터를 Tuple 형식으로 가져와서 List 에 담기
        System.out.println("결과");
        System.out.println(result);

        // Tuple을 Object[] 로 변환
        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());

        return list;
    }

    @Override
    public List<Member> members() {
        QMember member = QMember.member;

        // select * from member where name = 'user1' order by name desc;
        JPQLQuery<Member> query = from(member);
        query.where(member.name.eq("고객1")).orderBy(member.name.desc());
        JPQLQuery<Member> tuple = query.select(member);
        List<Member> list = tuple.fetch();
        return list;
    }

    @Override
    public List<Item> items() {
        // select * from item where name = '아이템3' and price > 20000
        QItem item = QItem.item;

        JPQLQuery<Item> query = from(item);
        query.where(item.name.eq("아이템3").and(item.price.gt(20000)));
        JPQLQuery<Item> tuple = query.select(item);
        List<Item> list = tuple.fetch();
        return list;
    }

}
