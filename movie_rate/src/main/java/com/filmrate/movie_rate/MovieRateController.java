package com.filmrate.movie_rate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/movie/rate")
public class MovieRateController {
    @Autowired
    private MovieRateRepository movieRateRepository;

    @GetMapping
    public List<MovieRate> getRates(){
        return movieRateRepository.findAll();
    }

    @GetMapping("{movieId}")
    public List<MovieRate> getRatesOfMovie(@PathVariable Integer movieId){
        return movieRateRepository.findAllByMovieId(movieId);
    }

    @PostMapping
    public void rateToMovie(@RequestBody MovieRate movieRate, Principal principal){
        if (!movieRateRepository.existsByUsernameAndMovieId(principal.getName(),movieRate.getMovieId())){
            movieRate.setId(0);
            movieRate.setUsername(principal.getName());
            movieRateRepository.save(movieRate);
        }
    }
}
