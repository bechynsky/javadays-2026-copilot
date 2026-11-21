package cz.javadays.feedback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
}
