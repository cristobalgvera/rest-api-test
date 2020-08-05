package cl.crisgvera.moviecatalogservice.resource;

import cl.crisgvera.moviecatalogservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/{id}")
    public UserCatalogItem getCatalog(@PathVariable("id") String userId) {

        // Get all rated movies IDs
//        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);

        // This do the same of above committed code but above object (restTemplate) will be deprecated
        UserRating ratings = webClientBuilder.build()
                .get()
                .uri("http://ratings-data-service/ratingsdata/users/" + userId) // 'ratings-data-service' is the name of service on Eureka, check it on application.properties
                .retrieve()
                .bodyToMono(UserRating.class)
                .block();

        List<CatalogItem> catalogItems = ratings.getUserRatings().stream().map(rating -> {
            // For each movie ID, call movie info service and get details
            // .getForObject() method populate an object with rest response of url site
            // Object to populate must have an empty constructor else will throw an error
//            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);


            // This do the same of above committed code but above object (restTemplate) will be deprecated
            Movie movie = webClientBuilder.build()
                    .get() // Request type. Can be post, put, delete, and so on
                    .uri("http://movie-info-service/movies/" + rating.getMovieId()) // URI where get the consume
                    .retrieve() // Kind a fetch
                    .bodyToMono(Movie.class) // It's a promise for when consume is available to populate the class
                    .block(); // .block() "stop" method execution and resume it when Movie class instance are populate


            // Put them all together
            return new CatalogItem(movie.getName(), movie.getOverview(), rating.getRating());
        })
                .collect(Collectors.toList());

        return new UserCatalogItem(catalogItems);
    }
}
