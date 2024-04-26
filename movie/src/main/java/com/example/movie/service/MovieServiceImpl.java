package com.example.movie.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.movie.dto.MovieDto;
import com.example.movie.dto.MovieImageDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PageResultDto;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.repository.MovieImageRepository;
import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public PageResultDto<MovieDto, Object[]> getList(PageRequestDto pageRequestDto) {
        Pageable pageable = pageRequestDto.getPageable(Sort.by("mno").descending());
        Page<Object[]> result = movieImageRepository.getTotalList(pageable);

        Function<Object[], MovieDto> function = (en -> entityToDto((Movie) en[0],
                (List<MovieImage>) Arrays.asList((MovieImage) en[1]),
                (Double) en[3], (Long) en[2]));
        return new PageResultDto<>(result, function);
    }

    @Override
    public MovieDto getRow(Long mno) {
        List<Object[]> result = movieImageRepository.getMovieRow(mno);

        // MovieImage 만 바뀜
        // [Movie(mno=1, title=Movie 1), MovieImage(inum=2,
        // uuid=f01b359a-76f0-407f-931c-7a73c47c15ac, imgName=img1.jpg, path=null), 1,
        // 1.0]
        // [Movie(mno=1, title=Movie 1), MovieImage(inum=1,
        // uuid=53b28cf7-7136-4a5e-b4d3-896aa41b6715, imgName=img0.jpg, path=null), 1,
        // 1.0]
        Movie movie = (Movie) result.get(0)[0];
        Long reviewCnt = (Long) result.get(0)[2];
        Double avg = (Double) result.get(0)[3];

        // result 길이만큼 반복
        // List<MovieImage> movieImages = result.stream().map(en ->
        // (MovieImage)en[1]).collect(Collectors.toList()); 도 가능
        List<MovieImage> movieImages = new ArrayList<>();
        result.forEach(arr -> {
            MovieImage movieImage = (MovieImage) arr[1];
            movieImages.add(movieImage);
        });
        return entityToDto(movie, movieImages, avg, reviewCnt);

    }

    @Transactional
    @Override
    public void movieRemove(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();
        movieImageRepository.deleteByMovie(movie);
        reviewRepository.deleteByMovie(movie);
        movieRepository.delete(movie);
    }

}
