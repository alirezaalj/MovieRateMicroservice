package com.filmrate.movie_rate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRateRepository extends JpaRepository<MovieRate,Integer> {
    List<MovieRate> findAllByMovieId(Integer movieId);

    boolean existsByUsernameAndMovieId(String username,Integer movieId);
}
