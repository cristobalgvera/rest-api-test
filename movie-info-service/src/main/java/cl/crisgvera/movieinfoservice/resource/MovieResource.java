package cl.crisgvera.movieinfoservice.resource;

import cl.crisgvera.movieinfoservice.model.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieResource {
    @GetMapping("/{id}")
    public Movie getMovieInfo(@PathVariable("id") String movieId) {
        return new Movie(movieId, "Test name");
    }
}
