package com.example.movie.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.movie.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("select m, avg(r.grade), count(distinct r) from Movie m left join Review r on r.movie = m group by m")
    // @Query("select m, min(mi), avg(r.grade), count(distinct r) from Movie m left
    // join MovieImage mi on mi.movie = m "
    // +
    // " left join Review r on r.movie = m group by m")
    Page<Object[]> getListPage2(Pageable pageable);

    @Query(value = "SELECT * FROM movie m LEFT JOIN (SELECT r.movie_mno, count(*), AVG(r.grade) " +
            " FROM review r GROUP BY r.movie_mno ) r1 " +
            " ON m.mno = r1.movie_mno " +
            " LEFT JOIN (SELECT mi2.* FROM MOVIE_IMAGE mi2 WHERE mi2.inum " +
            " IN (SELECT min(mi.inum) FROM MOVIE_IMAGE mi GROUP BY mi.movie_mno)) a ON m.mno = a.movie_mno ", nativeQuery = true)
    Page<Object[]> getListPage(Pageable pageable);
}
