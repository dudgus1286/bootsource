package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Memo;

// jUnit : 테스트 라이브러리

@SpringBootTest // 테스트 전용 클래스
public class MemoRepositoryTest {
    // 객체 생성
    // private MemoRepository memoRepository = new MemoRepository() {
    // }; - 그냥 생성자로 하면 안 됨(인터페이스라서)
    @Autowired // MemoRepository memoRepository = new MemoRepositoryImpl();
    private MemoRepository memoRepository;

    @Test // 테스트 메소드(리턴타입은 void)
    public void insertMemo() {
        // 100개의 메모 입력
        for (int i = 1; i < 101; i++) {
            Memo memo = new Memo();
            memo.setMemoText("MemoText " + i);
            // save(): insert, update 구문 실행하려고 할 때 사용
            memoRepository.save(memo); // == dao.insert()
        }
    }

    @Test
    public void getMemo() {
        // 특정 아이디의 메모 조회
        // Optional : null 체크할 수 있는 메소드 보유
        Optional<Memo> result = memoRepository.findById(21L);

        System.out.println(result.get());
    }

    @Test
    public void getMemoList() {
        // 전체 메모 조회
        List<Memo> result = memoRepository.findAll();
        for (Memo memo : result) {
            System.out.println(memo);
        }
    }

    @Test
    public void updateMemo() {
        // 메모 수정
        // 업데이트 할 entity 찾기
        Optional<Memo> result = memoRepository.findById(25L);
        Memo memo = result.get();

        // 수정
        memo.setMemoText("update Title....");
        // memoRepository.save(memo);
        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void deleteMemo() {
        // 삭제할 entity 찾기
        Optional<Memo> result = memoRepository.findById(24L);
        Memo memo = result.get();

        // 삭제
        memoRepository.delete(memo);
        System.out.println("삭제 memo " + memoRepository.findById(24L));
    }

    @Test
    public void jpqlTest() {
        // 5보다 작은 메모 조회
        List<Memo> list1 = memoRepository.findByMnoLessThan(5L);
        System.out.println(list1.size());

        // 10보다 작은 메모 내림차순 정렬해서 조회
        List<Memo> list2 = memoRepository.findByMnoLessThanOrderByMnoDesc(10L);
        System.out.println(list2.size());

        // 50 이상 70 이하 메모 조회
        List<Memo> list3 = memoRepository.findByMnoGreaterThanEqualAndMnoLessThanEqual(50L, 70L);
        System.out.println(list3.size());
        list3 = memoRepository.findByMnoBetween(50L, 70L);
        System.out.println(list3.size());
    }
}
