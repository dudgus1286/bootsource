package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Child;
import com.example.jpa.entity.Parent;

import jakarta.transaction.Transactional;

@SpringBootTest
public class ParentRepositoryTest {
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;

    @Test
    public void insertTest() {
        // 부모 하나에 자식 2 명
        Parent p = Parent.builder().name("parent1").build();

        Child c1 = Child.builder().name("child1").parent(p).build();
        p.getChild().add(c1);
        Child c2 = Child.builder().name("child2").parent(p).build();
        p.getChild().add(c2);

        parentRepository.save(p);
        // Parent 엔티티에 child 리스트 필드에 cascade = CascadeType.ALL 설정하지 않았을 때는 부모만 추가되었음

        // 자식만 추가하려고 하면 오류 남
        // childRepository.save(child1);
        // childRepository.save(child2);
    }

    @Test
    // 부모자식 데이터 삭제 (기존 방식)
    public void deleteTest() {
        // 부모가 자식을 가지고 있으면 자식의 해당 부모 아이디 먼저 지우고서 삭제해야 함(기본)
        Parent p = Parent.builder().id(1L).build();

        // 부모의 아이디를 null로 지정 후 부모 제거
        // Child c1 = Child.builder().id(1L).parent(null).build();
        // Child c2 = Child.builder().id(2L).parent(null).build();
        // childRepository.save(c1);
        // childRepository.save(c2);

        // 자식을 제거 후 부모도 제거
        Child c1 = Child.builder().id(1L).parent(null).build();
        Child c2 = Child.builder().id(2L).parent(null).build();

        childRepository.delete(c1);
        childRepository.delete(c2);

        parentRepository.delete(p);
    }

    @Test
    public void deleteCascadeTest() {
        // @OneToMany 에 cascade = CascadeType.ALL 설정 후에는 부모 먼저 삭제하면 자식도 자동으로 삭제됨
        Parent p = Parent.builder().id(102L).build();

        Child c1 = Child.builder().id(152L).parent(null).build();
        p.getChild().add(c1);
        Child c2 = Child.builder().id(153L).parent(null).build();
        p.getChild().add(c2);

        parentRepository.delete(p);

    }

    @Transactional
    @Test
    public void deleteOrphanTest() {
        // 부모 개체에 orphanRemoval 설정해서 고아가 된 자식 개체 자동으로 삭제하는 기능 제공
    }
}
