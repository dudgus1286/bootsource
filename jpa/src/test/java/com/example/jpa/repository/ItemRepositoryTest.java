package com.example.jpa.repository;

import java.util.Optional;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Item;
import com.example.jpa.entity.ItemSellStatus;

@SpringBootTest
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void createTest() {
        LongStream.rangeClosed(1, 10).forEach(i -> {
            Item item = Item.builder()
                    // .id(i)
                    .itemNm("운동화" + i)
                    .price((int) (95000 * i))
                    .stockNumber(30)
                    .itemSellStatus(ItemSellStatus.SELL)
                    .build();
            itemRepository.save(item);
        });
    }

    @Test
    public void readTest() {
        System.out.println(itemRepository.findById(2L));
        System.out.println("--------------------");
        itemRepository.findAll().forEach(board -> System.out.println(board));
    }

    @Test
    public void updateTest() {
        // entity 찾기
        Optional<Item> result = itemRepository.findById(8L);
        Item item = result.get();
        // 수정
        item.setItemNm("구두");
        item.setPrice(600);
        itemRepository.save(item);
        System.out.println(result);
    }

    @Test
    public void deleteTest() {
        // entity 찾기
        Optional<Item> result = itemRepository.findById(6L);
        // 삭제
        itemRepository.delete(result.get());
        System.out.println(itemRepository.findById(6L));
    }
}
