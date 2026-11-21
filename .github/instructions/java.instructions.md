---
applyTo: "**/*.java"
---
# Pravidla pro Java soubory

- Cílová verze je Java 25 – používej moderní konstrukce tam, kde zlepší čitelnost
  (`record`, `var` u zřejmých typů, `switch` výrazy, text blocks).
- Spring komponenty: `@RestController`, `@Service`; závislosti jen přes konstruktor.
- Žádná logika v controlleru – controller jen převádí HTTP na volání služby.
- Neplatný vstup = `IllegalArgumentException` se srozumitelnou českou zprávou;
  controller ji převede na HTTP 400.
- Veřejné metody služeb mají krátký Javadoc (1–2 věty).
