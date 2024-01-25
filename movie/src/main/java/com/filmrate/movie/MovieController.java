package com.filmrate.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${app.movie.rate.host:http://localhost:8082}")
    private String movieRateHost;

    @GetMapping
    public List<Movie> getMovies(){
        return movieRepository.findAll();
    }

    @GetMapping("{id}")
    public MovieDetail getMovie(@PathVariable Integer id, Authentication authentication){
        Movie movie = movieRepository.findById(id)
                .orElseThrow();
        MovieDetail movieDetail=new MovieDetail();
        movieDetail.setId(movie.getId());
        movieDetail.setName(movie.getName());
        movieDetail.setDescription(movie.getDescription());

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION,"Bearer "+userDetails.getPassword());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<MovieRate[]> movieRateResponse = restTemplate.exchange(movieRateHost+"/api/movie/rate/"+movie.getId(), HttpMethod.GET, entity, MovieRate[].class);

        if (movieRateResponse.getStatusCode().is2xxSuccessful()){
            MovieRate[] movieRates = movieRateResponse.getBody();
            if (movieRates!=null){
                movieDetail.setRates(Arrays.stream(movieRates).toList());
                movieDetail.setRate(movieDetail.getRates().stream()
                        .collect(Collectors.averagingDouble(MovieRate::getRate)));
            }
        }

        return movieDetail;
    }

    @PostMapping
    public void addMovie(@RequestBody Movie movie){
        movie.setId(0);
        movieRepository.save(movie);
    }
}
