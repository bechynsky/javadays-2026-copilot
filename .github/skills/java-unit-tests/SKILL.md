---
name: java-unit-tests
description: Píše a spouští unit testy pro Java / Spring Boot kód v tomto repozitáři (JUnit, AssertJ, MockMvc). Použij, když uživatel chce přidat, opravit nebo rozšířit testy nebo ověřit, že kód funguje.
---
# Unit testy pro Java (JUnit + AssertJ + MockMvc)

## Postup
1. Přečti celou třídu, kterou máš testovat, a třídy, které používá.
2. Pro každou veřejnou metodu vypiš scénáře: běžný případ, hraniční hodnoty, chybový stav.
3. Testy ulož do `src/test/java` do stejného balíčku jako testovanou třídu, název `<Trida>Test`.
4. Každý test má strukturu Arrange / Act / Assert (oddělené prázdným řádkem).
5. Každý test má `@DisplayName` česky – výstup testů je pak čitelný pro celý tým.
6. Více vstupních hodnot = `@ParameterizedTest` + `@ValueSource` nebo `@CsvSource`.
7. Controller testuj přes `MockMvcBuilders.standaloneSetup(...)` – bez startu celé aplikace.
8. Spusť `mvn test` ve složce s `pom.xml` a oprav padající testy.
9. Produkční kód neměň bez souhlasu uživatele. Když test odhalí chybu, popiš ji a navrhni opravu.

## Vzor testu služby
```java
@Test
@DisplayName("Průměr ze 4 a 5 hvězdiček je 4.5")
void averageIsCalculated() {
    // Arrange
    var service = new RatingService();
    service.addRating("copilot", 4);
    service.addRating("copilot", 5);

    // Act
    var summary = service.summary("copilot");

    // Assert
    assertThat(summary.average()).isEqualTo(4.5);
}
```

## Vzor parametrizovaného testu
```java
@ParameterizedTest(name = "{0} hvězdiček je neplatné hodnocení")
@ValueSource(ints = {0, 6, -1})
void invalidStarsAreRejected(int stars) {
    assertThatThrownBy(() -> service.addRating("copilot", stars))
        .isInstanceOf(IllegalArgumentException.class);
}
```

## Hotovo, když
- `mvn test` končí `BUILD SUCCESS`
- každá veřejná metoda má aspoň jeden pozitivní a jeden negativní test
- v odpovědi shrneš počet testů a co pokrývají
