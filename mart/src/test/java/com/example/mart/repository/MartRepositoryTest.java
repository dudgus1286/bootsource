package com.example.mart.repository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;
import com.example.mart.entity.OrderStatus;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MartRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void insertTest() {
        // 멤버 3
        memberRepository.save(
                Member.builder()
                        .name("고객1").zipcode("123").city("서울").street("1번가")
                        .build());
        memberRepository.save(
                Member.builder()
                        .name("고객2").zipcode("456").city("광주").street("2번가")
                        .build());
        memberRepository.save(
                Member.builder()
                        .name("고객3").zipcode("789").city("부산").street("3번가")
                        .build());
        // 아이템 3
        itemRepository.save(
                Item.builder()
                        .name("아이템1").price(10000).stockQuantity(30)
                        .build());
        itemRepository.save(
                Item.builder()
                        .name("아이템2").price(15000).stockQuantity(20)
                        .build());
        itemRepository.save(
                Item.builder()
                        .name("아이템3").price(30000).stockQuantity(25)
                        .build());
    }

    // 주문
    @Test
    public void orderInsertTest() {
        // 멤버 정보
        Member member = Member.builder().id(1L).build();
        // 아이템 정보
        // Item item = Item.builder().id(1L).price(10000).build();
        Item item = itemRepository.findById(1L).get();
        // 주문 수량
        int count = 2;

        // 주문 , 주문상품
        Order order = Order.builder()
                .member(member).orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .build();
        OrderItem orderItem = OrderItem.builder()
                .item(item).order(order).orderPrice(item.getPrice() * count).count(count).build();

        orderRepository.save(order);
        orderItemRepository.save(orderItem);
    }

    @Test
    public void readTest() {
        // 전체 회원 조회
        memberRepository.findAll().forEach(member -> System.out.println(member));

        // 전체 아이템 조회
        itemRepository.findAll().forEach(item -> System.out.println(item));

        // 주문아이템 조회 시 주문정보도 확인 (OrderItem(N):Order(1))
        // 객체 그래프 탐색 => N:1 관계에서 N쪽의 엔티티는 fetch 설정이 기본적으로 FetchType.EAGER 이기 때문
        orderItemRepository.findAll().forEach(entity -> {
            System.out.println(entity);
            System.out.println("상품정보 " + entity.getItem());
            System.out.println("구매자 " + entity.getOrder().getMember().getName());
        });

    }

    @Test
    public void updateTest() {
        // 멤버 주소 수정
        Member member = memberRepository.findById(2L).get();
        member.setCity("전주");
        memberRepository.save(member);
        System.out.println(memberRepository.findById(2L).get());

        // 아이템 가격 수정
        Item item = itemRepository.findById(3L).get();
        item.setPrice(25000);
        itemRepository.save(item);
        System.out.println(itemRepository.findById(3L).get());

        // 주문상태 수정
        Order order = orderRepository.findById(4L).get();
        order.setOrderStatus(OrderStatus.CANCEL);
        orderRepository.save(order);
        System.out.println(orderRepository.findById(4L).get());
    }

    @Test
    public void deleteTest() {
        // 주문 제거
        // Order order = orderRepository.findById(2L).get();
        // orderItemRepository.delete(orderItemRepository.findById(order.getId()).get());
        // orderRepository.delete(order);

        // 주문 아이템 제거 후 주문 제거
        orderItemRepository.delete(OrderItem.builder().id(1L).build());
        orderRepository.deleteById(1L);
    }

    // @Transactional
    @Test
    public void readTest2() {
        // Order : OrderItem 중에서 Order를 기준으로 OrderItem 조회

        // @OneToMany 를 이용해 조회
        // @OneToMany - 관련 있는 entity를 처음부터 갖고 오지 않음
        // 1. 메소드에 @Transactional 걸어서 한꺼번에 갖고 오게 하기
        // 2. @OneToMany 의 FetchType 변경
        Order order = orderRepository.findById(1L).get();
        System.out.println(order); // @ToString에서 @OneToMany 건 변수 제외하지 않으면 에러 발생
        System.out.println(order.getOrderItems());
    }

    @Transactional
    @Test
    public void readTest3() {
        // 회원을 통해서 해당 회원의 주문내역 조회 (트랜잭션 활용)
        Member member = memberRepository.findById(1L).get();
        System.out.println(member);
        System.out.println(member.getOrders());
    }

}
