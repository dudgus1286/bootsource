package com.example.movie.repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.movie.constant.MemberRole;
import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.entity.Review;

@SpringBootTest
public class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieImageRepository movieImageRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void movieInsertTest() {
        // 영화 / 영화이미지 샘플 데이터 추가
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Movie movie = Movie.builder().title("Movie " + i).build();
            movieRepository.save(movie);

            int count = (int) (Math.random() * 5) + 1;

            for (int j = 0; j < count; j++) {
                MovieImage mImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("img" + j + ".jpg")
                        .build();
                movieImageRepository.save(mImage);
            }
        });

    }

    @Test
    public void memberInsertTest() {
        // 멤버 샘플 데이터 추가
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("mem" + i + "@naver.com")
                    .password(passwordEncoder.encode("1111"))
                    .role(MemberRole.MEMBER)
                    .nickname("reviewer" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void reviewInsertTest() {
        // 리뷰 샘플 데이터 추가
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Long mno = (long) (Math.random() * 100 + 1);
            Movie movie = Movie.builder().mno(mno).build();

            Long mid = (long) (Math.random() * 100 + 1);
            Member member = Member.builder().mid(mid).build();

            Review review = Review.builder()
                    .movie(movie)
                    .member(member)
                    .grade((int) (Math.random() * 5) + 1)
                    .text("이 영화에 대한.. " + i)
                    .build();
            reviewRepository.save(review);
        });
    }

    @Test
    public void movieListTest() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Object[]> list = movieRepository.getListPage(pageRequest);

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void movieListTest2() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Object[]> list = movieRepository.getListPage2(pageRequest);

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void movieImageListTest() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Object[]> list = movieImageRepository.getTotalList(pageRequest);

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void movieGetTest() {
        List<Object[]> result = movieImageRepository.getMovieRow(1L);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Transactional
    @Test
    public void movieRemoveTest() {
        Movie movie = Movie.builder().mno(1L).build();
        // 이미지 삭제
        movieImageRepository.deleteByMovie(movie);
        // 리뷰 삭제
        reviewRepository.deleteByMovie(movie);

        // 영화 삭제
        movieRepository.delete(movie);
        ;
    }
}
