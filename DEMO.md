# Scénář živého dema (18 minut)

## Den předem
1. Z tohoto repozitáře založ vlastní kopii a otevři ji v **Codespaces** (Code → Codespaces → Create).
   Devcontainer nainstaluje Javu 25, Maven, Node.js, Azure CLI, `azd` a Copilot CLI.
2. V Codespace se přihlas: `azd auth login` a `az login`.
3. **Záloha:** ve složce `reseni/` spusť `azd up` a poznamenej si URL – v demu na ni přepneš,
   protože vytvoření prostředků v Azure trvá několik minut.
4. Ověř MCP servery: v chatu *Chat: Open Customizations → MCP Servers* (nebo *MCP: List Servers*).
5. Vytvoř větev pro živé demo bez hotového řešení:
   `git switch -c demo-live && git rm -r -q reseni && git commit -m "Start dema"`
6. Založ issue z promptu č. 7 (`PROMPTY.md`) a **ráno před přednáškou** ho přiřaď Copilotovi –
   v demu ukážeš hotový pull request.
7. Zvětši písmo (Zoom 150 %), zavři nepotřebné panely, ztlum notifikace.

## Průběh
| Čas | Krok | Prompt |
|-----|------|--------|
| 0:00 | Prohlídka repozitáře: `AGENTS.md`, `.github/skills`, `.github/agents`, `.mcp.json` | – |
| 0:02 | Plán v režimu Plan, pak vytvoření aplikace v režimu Agent – komentuj plán a schvalování příkazů | č. 0, č. 1 |
| 0:06 | Vyzkoušení přes `curl` (200, 201, 400) | č. 1b |
| 0:08 | Unit testy přes skill `/java-unit-tests`, `mvn test` zeleně | č. 2 |
| 0:11 | Java Mentor vysvětlí kód (record, stream API, anotace Springu) | č. 3 |
| 0:13 | `/azure-deploy`: šablony, ověření přes Microsoft Learn MCP, `azd up` → přepni na zálohu | č. 4 |
| 0:16 | Copilot CLI v terminálu, pak cloud agent: issue → pull request, review a CI | č. 8, č. 7 |
| 0:18 | Konec dema, zpět do slajdů | – |

## Když se něco pokazí
- Demo nabírá zpoždění → vynech Java Mentora (krok 0:11) a ukaž jen slajd s ukázkou odpovědi.
- Agent se zasekne nebo udělá nesmysl → zastav ho, řekni nahlas proč a pokračuj z `reseni/`
  (`git checkout main -- reseni`). I to je lekce: člověk řídí, AI navrhuje.
- Pomalá síť na konferenci → mobilní hotspot jako záloha, `azd up` jen ze zálohy.
- Model odpovídá jinak než na zkoušce → to je normální (LLM nejsou deterministické);
  ukaž, jak prompt zpřesnit (Specific) a zkus znovu.
