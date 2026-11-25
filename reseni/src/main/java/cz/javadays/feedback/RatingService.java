package cz.javadays.feedback;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

/**
 * Ukládá hodnocení přednášek v paměti a počítá jejich průměr.
 */
@Service
public class RatingService {

    private static final int MIN_STARS = 1;
    private static final int MAX_STARS = 5;

    private final Map<String, List<Integer>> ratingsByTalk = new ConcurrentHashMap<>();

    /**
     * Přidá hodnocení přednášky. Povolené jsou 1 až 5 hvězdiček.
     */
    public void addRating(String talkId, int stars) {
        if (stars < MIN_STARS || stars > MAX_STARS) {
            throw new IllegalArgumentException(
                    "Hodnocení musí být 1 až 5 hvězdiček, ale bylo: " + stars);
        }
        ratingsByTalk.computeIfAbsent(talkId, id -> new CopyOnWriteArrayList<>()).add(stars);
    }

    /**
     * Vrátí počet hodnocení a průměr pro danou přednášku (0, pokud zatím nikdo nehodnotil).
     */
    public RatingSummary summary(String talkId) {
        List<Integer> ratings = ratingsByTalk.getOrDefault(talkId, List.of());
        double average = ratings.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
        double rounded = Math.round(average * 10) / 10.0;
        return new RatingSummary(talkId, ratings.size(), rounded);
    }

    /**
     * Vrátí nejvýše tři hodnocené přednášky seřazené podle průměru a počtu hodnocení.
     */
    public List<RatingSummary> topRated() {
        return ratingsByTalk.keySet().stream()
                .map(this::summary)
                .filter(summary -> summary.count() > 0)
                .sorted(Comparator.comparingDouble(RatingSummary::average)
                        .reversed()
                        .thenComparing(Comparator.comparingLong(RatingSummary::count).reversed()))
                .limit(3)
                .toList();
    }
}
