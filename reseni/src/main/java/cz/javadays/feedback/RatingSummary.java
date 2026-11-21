package cz.javadays.feedback;

/**
 * Souhrn hodnocení jedné přednášky: počet hodnocení a průměr zaokrouhlený na 1 desetinné místo.
 */
public record RatingSummary(String talkId, long count, double average) {
}
