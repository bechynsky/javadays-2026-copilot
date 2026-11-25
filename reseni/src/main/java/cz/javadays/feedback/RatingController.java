package cz.javadays.feedback;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API pro hodnocení přednášek. Jen převádí HTTP požadavky na volání {@link RatingService}.
 */
@RestController
@RequestMapping("/api/talks")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/{talkId}/ratings")
    @ResponseStatus(HttpStatus.CREATED)
    public RatingSummary rate(@PathVariable("talkId") String talkId,
                              @RequestBody RatingRequest request) {
        ratingService.addRating(talkId, request.stars());
        return ratingService.summary(talkId);
    }

    @GetMapping("/{talkId}/ratings")
    public RatingSummary summary(@PathVariable("talkId") String talkId) {
        return ratingService.summary(talkId);
    }

    @GetMapping("/top")
    public List<RatingSummary> topRated() {
        return ratingService.topRated();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleInvalidRating(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }
}
