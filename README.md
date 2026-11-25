# GitHub Copilot a vibe coding – ukázka z JavaDays 2026

Ukázkový repozitář k přednášce **„GitHub Copilot a vibe coding“** na konferenci
JavaDays 2026 (Praha, 25.–26. 11. 2026).

Ukazuje, jak s **GitHub Copilotem** postavit Java aplikaci, napsat k ní testy a nasadit ji do Azure –
a hlavně, jak Copilota **nakonfigurovat**, aby odvedl práci podle pravidel vašeho týmu:

| Co | Kde | K čemu |
|----|-----|--------|
| Instrukce pro agenty | [`AGENTS.md`](AGENTS.md) | stack, příkazy, pravidla, definice hotového |
| Instrukce pro Copilota | [`.github/copilot-instructions.md`](.github/copilot-instructions.md) | platí pro každý dotaz v repozitáři |
| Instrukce podle cesty | [`.github/instructions/`](.github/instructions) | pravidla jen pro `*.java` nebo `*.bicep` |
| Skills | [`.github/skills/`](.github/skills) | opakovatelné postupy: unit testy, nasazení do Azure |
| Vlastní agenti | [`.github/agents/`](.github/agents) | role: Java Mentor, Code Reviewer |
| MCP servery | [`.mcp.json`](.mcp.json) | Microsoft Learn (aktuální dokumentace), Azure |
| Prostředí | [`.devcontainer/`](.devcontainer) | Codespaces s Javou 25, Mavenem, azd a Copilot CLI |
| Cloud agent | [`.github/workflows/copilot-setup-steps.yml`](.github/workflows/copilot-setup-steps.yml) | příprava prostředí pro Copilot cloud agenta |
| Prompty | [`PROMPTY.md`](PROMPTY.md) | vzorové prompty z přednášky |
| Scénář dema | [`DEMO.md`](DEMO.md) | krok za krokem, včetně záložního plánu |
| Hotové řešení | [`reseni/`](reseni) | Spring Boot aplikace, testy, Bicep, `azure.yaml` |

## Aplikace: JavaDays Feedback API
Účastníci hodnotí přednášky 1–5 hvězdičkami. Data jsou jen v paměti.

| Metoda | Cesta | Popis |
|--------|-------|-------|
| GET | `/` | pozdrav – kontrola, že aplikace běží |
| POST | `/api/talks/{talkId}/ratings` | tělo `{"stars": 5}` → 201 + souhrn; mimo 1–5 → 400 |
| GET | `/api/talks/{talkId}/ratings` | `{"talkId": "...", "count": 2, "average": 4.5}` |
| GET | `/api/talks/top` | pole tří nejlépe hodnocených přednášek |

## Rychlý start
```bash
cd reseni
mvn test                 # unit testy
mvn spring-boot:run      # http://localhost:8080
azd up                   # nasazení do Azure App Service (placený plán B1)
azd down --purge         # úklid
```
Nejrychlejší cesta: **Code → Codespaces → Create codespace** – vše potřebné se nainstaluje samo.

Příklad získání nejlépe hodnocených přednášek:
```bash
curl http://localhost:8080/api/talks/top
```

## Kde se učit dál
- Kurz [GH-300T00-A: GitHub Copilot](https://learn.microsoft.com/en-us/training/courses/gh-300t00) a certifikace GitHub Copilot (GH-300)
- [Dokumentace GitHub Copilot](https://docs.github.com/copilot)
- [Agent Skills ve VS Code](https://code.visualstudio.com/docs/agent-customization/agent-skills)
- [Microsoft Learn MCP Server](https://learn.microsoft.com/en-us/training/support/mcp-developer-reference)
- Komunitní sbírka instrukcí, agentů a skills: [github/awesome-copilot](https://github.com/github/awesome-copilot)

## Poznámka
Složka `reseni/` je referenční výsledek, ke kterému by měl Copilot s prompty z `PROMPTY.md` dojít,
a slouží jako záloha pro živé demo. Výstup AI se pokaždé trochu liší a AI se může mýlit –
vždy čtěte diff a spouštějte testy.

Licence: MIT
