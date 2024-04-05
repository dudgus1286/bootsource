package com.example.mart.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "orderItems")
@Builder
@Table(name = "orders") // 테이블명으로 order 사용 불가
@Entity
public class Order {
    @SequenceGenerator(name = "mart_order_seq_gen", sequenceName = "mart_order_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mart_order_seq_gen")
    @Id
    @Column(name = "order_id")
    private Long id; // 주문번호

    @ManyToOne
    private Member member;

    @CreatedDate
    private LocalDateTime orderDate;

    // 주문상태 - ORDER, CANCEL
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Builder.Default // .builder() ~ .build() 할 때 강제로 초기화하도록 명령(기본적으로 만들지 않음)
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItem> orderItems = new ArrayList<>();
}
