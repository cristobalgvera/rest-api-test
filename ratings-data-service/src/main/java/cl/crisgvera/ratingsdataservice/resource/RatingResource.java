package cl.crisgvera.ratingsdataservice.resource;

import cl.crisgvera.ratingsdataservice.model.Rating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratingsdata")
public class RatingResource {
    @GetMapping("/{id}")
    public Rating getRating(@PathVariable("id") String movieId) {
        return new Rating(movieId, 4);
    }
}
