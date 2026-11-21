---
name: azure-deploy
description: Připraví šablony a nasadí Spring Boot aplikaci do Azure App Service (Linux, Java 25) pomocí Azure Developer CLI (azd) a Bicep. Použij, když uživatel chce aplikaci nasadit do Azure nebo vytvořit infrastrukturu či šablony pro Azure.
---
# Nasazení do Azure App Service (azd + Bicep)

Ve složce `templates/` vedle tohoto souboru jsou ověřené výchozí šablony.

## Než začneš
1. Ověř, že projekt jde sestavit: `mvn -B package -DskipTests`.
2. Přes Microsoft Learn MCP (`microsoft_docs_search`) ověř aktuální hodnotu `linuxFxVersion`
   pro Java SE na App Service (očekáváme `JAVA|25-java25`). Když se liší, uprav šablonu a řekni to.

## Postup
1. Zkopíruj šablony do projektu:
   - `templates/azure.yaml` → kořen projektu (vedle `pom.xml`)
   - `templates/main.bicep`, `templates/resources.bicep`, `templates/main.parameters.json` → `infra/`
2. Uprav jen to nutné (název aplikace, region). Výchozí: App Service plán B1 (Linux), HTTPS only.
3. Do šablon nikdy nedávej hesla, klíče ani connection stringy.
4. Ukaž uživateli shrnutí: co se v Azure vytvoří (resource group, App Service plán, webová aplikace)
   a že plán B1 je placený. **Počkej na výslovné potvrzení.**
5. Po potvrzení spusť `azd auth login` (pokud je potřeba) a `azd up`.
6. Na konci vypiš URL aplikace, ukázkový `curl` a příkaz pro úklid `azd down --purge`.

## Když něco selže
- Chyba přihlášení → `azd auth login`, případně `az login`.
- Aplikace neodpovídá → zkontroluj, že app setting `SERVER_PORT` je `80`, a podívej se do logů:
  `az webapp log tail -g <resource-group> -n <app-name>`.
