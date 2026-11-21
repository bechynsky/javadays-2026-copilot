---
applyTo: "**/*.bicep"
---
# Pravidla pro Bicep šablony

- Názvy prostředků skládej z `resourceToken` (uniqueString), ať jsou globálně unikátní.
- Každý prostředek má `tags`; webová aplikace navíc `azd-service-name: api`.
- Bezpečné výchozí hodnoty: `httpsOnly: true`, `minTlsVersion: '1.2'`, `ftpsState: 'Disabled'`.
- Žádná hesla ani klíče v šablonách ani v parametrech.
- Nejmenší rozumný tarif pro demo: App Service plán `B1` (Linux).
