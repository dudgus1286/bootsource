package com.example.movie.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.movie.dto.MovieDto;
import com.example.movie.dto.MovieImageDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PageResultDto;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;

public interface MovieService {
    PageResultDto<MovieDto, Object[]> getList(PageRequestDto pageRequestDto);

    MovieDto getRow(Long mno);

    void movieRemove(Long mno);

    Long movieInsert(MovieDto movieDto);

    public default MovieDto entityToDto(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt) {
        MovieDto movieDto = MovieDto.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .createdDate(movie.getCreatedDate())
                .lastModifiedDate(movie.getLastModifiedDate())
                .avg(avg != null ? avg : 0.0d)
                .reviewCnt(reviewCnt)
                .build();

        // 영화 상세 조회 => 이미지 모두 보여주기
        List<MovieImageDto> movieImageDtos = movieImages.stream().map(movieImage -> {
            return MovieImageDto.builder()
                    .inum(movieImage.getInum())
                    .uuid(movieImage.getUuid())
                    .imgName(movieImage.getImgName())
                    .path(movieImage.getImgName())
                    .build();
        }).collect(Collectors.toList());

        movieDto.setMovieImageDtos(movieImageDtos);
        return movieDto;
    }

    public default Map<String, Object> dtoToEntity(MovieDto dto) {
        Map<String, Object> entityMap = new HashMap<>();

        // Movie Entity 생성
        Movie movie = Movie.builder().mno(dto.getMno()).title(dto.getTitle()).build();

        // 생성된 Movie Entity 를 Map 에 담기 : put()
        entityMap.put("movie", movie);

        // List<MovieImageDto> movieImageDtos 를 List<MovieImage> 로 변환
        List<MovieImageDto> movieImageDtos = dto.getMovieImageDtos();
        // List<MovieImage> movieImages = new ArrayList<>();
        // if (movieImageDtos != null && movieImageDtos.size() > 0) {
        // for (MovieImageDto mDto : dto.getMovieImageDtos()) {
        // MovieImage movieImage = MovieImage.builder()
        // .imgName(mDto.getImgName())
        // .uuid(mDto.getUuid())
        // .path(mDto.getPath())
        // .build();
        // movieImages.add(movieImage);
        // }
        // }

        if (movieImageDtos != null && movieImageDtos.size() > 0) {
            List<MovieImage> movieImages = movieImageDtos.stream().map(mDto -> {
                MovieImage movieImage = MovieImage.builder()
                        .imgName(mDto.getImgName())
                        .uuid(mDto.getUuid())
                        .path(mDto.getPath())
                        .movie(movie)
                        .build();
                return movieImage;
            }).collect(Collectors.toList());

            // 변환이 끝난 Entity List 를 Map 에 담기
            entityMap.put("imgList", movieImages);
        }

        return entityMap;
    }
}
