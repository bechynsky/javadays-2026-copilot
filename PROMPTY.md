# Vzorové prompty – GitHub Copilot a vibe coding (JavaDays 2026)

**Kde je použít:** VS Code nebo Codespaces → Copilot Chat → režim **Agent** (pokud není uvedeno jinak).
Skills spustíš lomítkem (např. `/java-unit-tests`), vlastního agenta vybereš v seznamu agentů v chatu.
Stejné prompty fungují i v Copilot CLI (`copilot`) a jako zadání issue pro Copilot cloud agenta.

> **Pravidlo 4S** (kurz GH-300, modul *Introduction to prompt engineering with GitHub Copilot*):
> **Single** – jeden úkol · **Specific** – konkrétně · **Short** – stručně · **Surround** – kontext
> (otevřené soubory, `AGENTS.md`, instrukce, skills).

---

## 0. Nejdřív plán (režim Plan)
```text
Navrhni plán jednoduché REST API aplikace „JavaDays Feedback API“ podle AGENTS.md.
Vypiš soubory, které vzniknou, a endpointy. Zatím nic neměň.
```

## 1. Vytvoř aplikaci (režim Agent)
```text
Vytvoř v kořeni repozitáře Spring Boot aplikaci „JavaDays Feedback API“ podle AGENTS.md.
Java 25, Spring Boot 4.1, Maven, data jen v paměti.

Endpointy:
1. GET / vrátí text „Ahoj JavaDays 2026!“
2. POST /api/talks/{talkId}/ratings s JSON {"stars": 1–5} uloží hodnocení přednášky
3. GET /api/talks/{talkId}/ratings vrátí {"talkId", "count", "average"}
4. Hodnocení mimo 1–5 vrátí HTTP 400 s českou chybovou zprávou.

Na konci aplikaci spusť (mvn spring-boot:run) a ukaž mi dva příkazy curl, kterými ji vyzkouším.
```

### 1b. Vyzkoušej ručně (terminál)
```bash
curl http://localhost:8080/
curl -X POST http://localhost:8080/api/talks/copilot/ratings -H "Content-Type: application/json" -d '{"stars": 5}'
curl http://localhost:8080/api/talks/copilot/ratings
curl -X POST http://localhost:8080/api/talks/copilot/ratings -H "Content-Type: application/json" -d '{"stars": 7}'   # → 400
```

## 2. Unit testy (skill `/java-unit-tests`)
```text
/java-unit-tests Napiš unit testy pro RatingService a RatingController.
Pokryj: průměr bez hodnocení je 0, průměr ze 4 a 5 je 4.5, neplatné hodnoty 0 a 6
(parametrizovaný test), HTTP 201 a HTTP 400 u controlleru.
Spusť mvn test a oprav testy, které neprojdou. Produkční kód beze mě neměň.
```

## 3. Vysvětli mi kód (vlastní agent **Java Mentor**)
```text
Vysvětli mi RatingService po logických blocích. Co dělají record, stream API
a anotace @Service? Na konci mi dej jednu otázku k procvičení.
```

## 4. Nasazení do Azure (skill `/azure-deploy` + MCP)
```text
/azure-deploy Připrav nasazení této aplikace do Azure App Service (Linux, Java 25)
přes Azure Developer CLI. Vytvoř azure.yaml a Bicep šablony ve složce infra/.
Aktuální hodnotu runtime ověř přes Microsoft Learn MCP.
azd up spusť až po mém potvrzení a na konci mi dej URL aplikace.
```

Úklid po demu:
```bash
azd down --purge
```

## 5. CI pipeline (GitHub Actions)
```text
Přidej GitHub Actions workflow .github/workflows/ci.yml, které při push do main
a u každého pull requestu spustí mvn -B verify na Javě 25 (Temurin) s cache pro Maven.
```

## 6. Code review (vlastní agent **Code Reviewer**)
```text
Zkontroluj RatingController a RatingService. Co by se mohlo pokazit v produkci?
```

## 7. Zadání pro Copilot cloud agenta (GitHub issue → přiřadit Copilotovi)
```text
Název: Přidat endpoint s nejlépe hodnocenými přednáškami

Přidej GET /api/talks/top, který vrátí 3 přednášky s nejvyšším průměrem
(při shodě rozhoduje vyšší počet hodnocení).

Akceptační kritéria:
- odpověď je JSON pole objektů {talkId, count, average}
- přednášky bez hodnocení se nevrací
- unit testy pro službu i controller, mvn test prochází
- README obsahuje příklad volání přes curl
```

## 8. Copilot CLI (terminál)
```bash
copilot                      # interaktivní režim v aktuální složce
> Spusť testy a shrň mi výsledek jednou větou.
> Proč mi padá build? Navrhni opravu a ukaž diff.
> /mcp                       # přehled MCP serverů

copilot -p "Vypiš endpointy této aplikace jako tabulku"   # jednorázový dotaz
```

## 9. Bonus – refaktoring a dokumentace
```text
Refaktoruj RatingService tak, aby úložiště bylo za rozhraním RatingRepository
(implementace v paměti). Chování ani testy se nesmí změnit. Nejdřív mi ukaž plán.
```
```text
Doplň README: co aplikace dělá, jak ji spustit lokálně, jak spustit testy a jak ji nasadit do Azure.
```

---

## Jak NE (anti-vzory)
| Prompt | Co porušuje |
|--------|-------------|
| „Udělej mi aplikaci.“ | Specific – Copilot neví, jakou |
| „Vytvoř aplikaci, testy, nasaď do Azure a přidej monitoring.“ | Single – čtyři úkoly najednou |
| Zadání na dvě strany textu | Short – důležité se ztratí |
| Prázdné repo bez `AGENTS.md` a instrukcí | Surround – chybí kontext |
