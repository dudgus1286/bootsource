package com.example.mart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "order")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Delivery extends BaseEntity {
    @SequenceGenerator(name = "mart_delivery_seq_gen", sequenceName = "mart_delivery_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mart_delivery_seq_gen")
    @Id
    @Column(name = "DELIVERY_ID")
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String zipcode;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStauts;

    // 외래키 거는 엔티티 양쪽 다 @OneToOne 걸 수 있음, 단 확실히 주인이 되는 엔티티가 누군지 명시해야함
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;
}
