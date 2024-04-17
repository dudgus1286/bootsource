package com.example.board.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.board.dto.BoardDto;
import com.example.board.dto.PageRequestDto;
import com.example.board.entity.Board;
import com.example.board.entity.QBoard;
import com.example.board.entity.QMember;
import com.example.board.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);

    }

    @Override
    public Page<Object[]> list(String type, String keyword, Pageable pagealbe) {
        log.info("Board + Reply + Member join");

        // Q클래스 사용
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        // left join
        /*
         * SQL :
         * SELECT b.bno, b.title, b.content, b.REG_DATE ,m.EMAIL
         * FROM BOARD b
         * LEFT JOIN "MEMBER" m ON b.WRITER_EMAIL = m.EMAIL
         */
        // @Query("select b,m from Board b left join b.writer m")
        JPQLQuery<Board> query = from(board);
        query.leftJoin(board.writer, member);

        // subquery => JPAExpressions
        /*
         * SQL :
         * (
         * SELECT COUNT(r.board_bno)
         * FROM REPLY r WHERE b.bno = r.board_bno
         * GROUP BY r.board_bno
         * ) AS cnt
         */
        JPQLQuery<Long> replyCount = JPAExpressions
                .select(reply.rno.count().as("replycnt"))
                .from(reply)
                .where(reply.board.eq(board))
                .groupBy(reply.board);

        JPQLQuery<Tuple> tuple = query.select(board, member, replyCount);

        // 검색
        BooleanBuilder builder = new BooleanBuilder();
        // WHERE gno > 0 AND
        // gno 는 0 보다 크다(PK) 선언
        builder.and(board.bno.gt(0L));

        // 검색 타입이 있는 경우
        // title LIKE '%keyword%' OR content LIKE '%keyword%' OR writer LIKE '%keyword%'
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(board.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(board.content.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(member.email.contains(keyword));
        }
        builder.and(conditionBuilder);
        tuple.where(builder);

        // 페이지 나누기 추가 -
        // sort 지정
        Sort sort = pagealbe.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder<Board> orderByExpression = new PathBuilder<>(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        // 페이지 처리
        tuple.offset(pagealbe.getOffset());
        tuple.limit(pagealbe.getPageSize());

        List<Tuple> result = tuple.fetch();
        // 전체 개수
        long count = tuple.fetchCount();

        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());

        return new PageImpl<>(list, pagealbe, count);
    }

    @Override
    public Object[] getRow(Long bno) {
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        // left join
        /*
         * SQL :
         * SELECT b.bno, b.title, b.content, b.REG_DATE ,m.EMAIL
         * FROM BOARD b
         * LEFT JOIN "MEMBER" m ON b.WRITER_EMAIL = m.EMAIL
         */
        // @Query("select b,m from Board b left join b.writer m")
        JPQLQuery<Board> query = from(board);
        query.leftJoin(board.writer, member);
        // where board.bno = ?
        query.where(board.bno.eq(bno));

        // subquery => JPAExpressions
        /*
         * SQL :
         * (
         * SELECT COUNT(r.board_bno)
         * FROM REPLY r WHERE b.bno = r.board_bno
         * GROUP BY r.board_bno
         * ) AS cnt
         */
        JPQLQuery<Long> replyCount = JPAExpressions
                .select(reply.rno.count().as("replycnt"))
                .from(reply)
                .where(reply.board.eq(board))
                .groupBy(reply.board);

        JPQLQuery<Tuple> tuple = query.select(board, member, replyCount);

        Tuple result = tuple.fetch().get(0);

        return result.toArray();
    }

}
