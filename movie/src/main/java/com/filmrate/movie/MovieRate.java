package com.filmrate.movie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieRate {

    private int id;
    private int movieId;
    private String username;
    private double rate;
}
