package com.example.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Embeddable // 다른 엔티티에 포함할 것을 의미
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
