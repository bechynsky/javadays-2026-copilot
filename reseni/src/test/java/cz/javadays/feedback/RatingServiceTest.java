package cz.javadays.feedback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RatingServiceTest {

    private final RatingService service = new RatingService();

    @Test
    @DisplayName("Přednáška bez hodnocení má počet 0 a průměr 0")
    void emptyTalkHasZeroAverage() {
        // Act
        RatingSummary summary = service.summary("nova-prednaska");

        // Assert
        assertThat(summary.count()).isZero();
        assertThat(summary.average()).isZero();
    }

    @Test
    @DisplayName("Průměr ze 4 a 5 hvězdiček je 4.5")
    void averageIsCalculated() {
        // Arrange
        service.addRating("copilot", 4);
        service.addRating("copilot", 5);

        // Act
        RatingSummary summary = service.summary("copilot");

        // Assert
        assertThat(summary.count()).isEqualTo(2);
        assertThat(summary.average()).isEqualTo(4.5);
    }

    @Test
    @DisplayName("Průměr se zaokrouhlí na jedno desetinné místo")
    void averageIsRoundedToOneDecimal() {
        // Arrange
        service.addRating("copilot", 5);
        service.addRating("copilot", 4);
        service.addRating("copilot", 4);

        // Act
        RatingSummary summary = service.summary("copilot");

        // Assert – 13 / 3 = 4.333… → 4.3
        assertThat(summary.average()).isEqualTo(4.3);
    }

    @Test
    @DisplayName("Hodnocení různých přednášek se nemíchají")
    void talksAreIndependent() {
        // Arrange
        service.addRating("copilot", 5);
        service.addRating("kotlin", 1);

        // Act + Assert
        assertThat(service.summary("copilot").average()).isEqualTo(5.0);
        assertThat(service.summary("kotlin").average()).isEqualTo(1.0);
    }

    @ParameterizedTest(name = "{0} hvězdiček je neplatné hodnocení")
    @ValueSource(ints = {0, 6, -1})
    void invalidStarsAreRejected(int stars) {
        assertThatThrownBy(() -> service.addRating("copilot", stars))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1 až 5");
    }

    @Test
    @DisplayName("Top 3 přednášky jsou seřazené podle průměru a počtu hodnocení")
    void topRatedTalksAreSortedAndLimited() {
        // Arrange
        service.addRating("one", 5);
        service.addRating("two", 4);
        service.addRating("two", 4);
        service.addRating("three", 4);
        service.addRating("four", 2);
        service.addRating("five", 1);

        // Act
        List<RatingSummary> topRated = service.topRated();

        // Assert
        assertThat(topRated).extracting(RatingSummary::talkId)
                .containsExactly("one", "two", "three");
        assertThat(topRated.get(1).count()).isEqualTo(2);
    }

    @Test
    @DisplayName("Top bez hodnocení vrátí prázdné pole")
    void topRatedIsEmptyWithoutRatings() {
        // Act
        List<RatingSummary> topRated = service.topRated();

        // Assert
        assertThat(topRated).isEmpty();
    }
}
