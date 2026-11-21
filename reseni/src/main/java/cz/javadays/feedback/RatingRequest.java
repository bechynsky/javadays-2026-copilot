package cz.javadays.feedback;

/**
 * Tělo požadavku na nové hodnocení, např. {"stars": 5}.
 */
public record RatingRequest(int stars) {
}
