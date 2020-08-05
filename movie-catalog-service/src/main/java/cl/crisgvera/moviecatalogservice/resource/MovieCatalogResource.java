package cl.crisgvera.moviecatalogservice.resource;

import cl.crisgvera.moviecatalogservice.model.CatalogItem;
import cl.crisgvera.moviecatalogservice.model.Movie;
import cl.crisgvera.moviecatalogservice.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{id}")
    public List<CatalogItem> getCatalog(@PathVariable("id") String userId) {

        // Get all rated movies IDs
        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 4),
                new Rating("5678", 3)
        );

        // For each movie ID, call movie info service and get details
        // Put them all together
        return ratings.stream().map(rating -> {
            // .getForObject() method populate an object with rest response of url site
            // Object to populate must have an empty constructor else will throw an error
            Movie movie = restTemplate.getForObject("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(), "Test", rating.getRating());
        })
                .collect(Collectors.toList());
    }
}
