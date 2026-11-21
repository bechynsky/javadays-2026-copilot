# AGENTS.md – JavaDays 2026 Feedback API

Tento soubor čtou AI agenti (GitHub Copilot v IDE, Copilot CLI i Copilot cloud agent).
Je to „README pro agenty“: stack, příkazy a pravidla projektu na jednom místě.

## O projektu
Jednoduché REST API, ve kterém účastníci konference hodnotí přednášky 1–5 hvězdičkami.
Slouží jako ukázka pro přednášku **GitHub Copilot a vibe coding** (JavaDays 2026).

## Stack
- Java 25, Spring Boot 4.1, Maven
- Testy: JUnit (Jupiter API), AssertJ, MockMvc
- Nasazení: Azure App Service (Linux, Java SE) přes Azure Developer CLI (`azd`) + Bicep
- Bez databáze – data drží aplikace jen v paměti

## Příkazy
| Co | Příkaz |
|----|--------|
| Sestavit | `mvn -B package` |
| Spustit testy | `mvn test` |
| Spustit lokálně | `mvn spring-boot:run` → http://localhost:8080 |
| Nasadit do Azure | `azd up` – **jen po výslovném potvrzení člověkem** |
| Smazat z Azure | `azd down --purge` |

Hotové referenční řešení je ve složce `reseni/` (příkazy spouštěj v ní).

## Struktura
```
src/main/java/cz/javadays/feedback/   aplikace (controller, service, record DTO)
src/test/java/cz/javadays/feedback/   unit testy
infra/                                Bicep šablony pro Azure
azure.yaml                            konfigurace Azure Developer CLI
```

## Pravidla pro kód
- Malé, čitelné třídy. Žádný Lombok. Přenosové objekty (DTO) jako `record`.
- Závislosti předávej konstruktorem (constructor injection), ne přes `@Autowired` na poli.
- Názvy tříd, metod a commit zprávy anglicky; chybové hlášky pro uživatele česky.
- Každá nová funkce = nový test.
- Nepřidávej nové knihovny bez vysvětlení, proč jsou potřeba.

## Bezpečnost
- Nikdy necommituj tajemství (klíče, hesla, connection stringy, tokeny).
- Příkazy, které mění Azure (`azd up`, `azd down`, `az ... create/delete`), spouštěj až po potvrzení.

## Definice hotového
- `mvn test` končí `BUILD SUCCESS`
- README popisuje, jak novou funkci vyzkoušet
- Změna je malá a srozumitelná pro code review
