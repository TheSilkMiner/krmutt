# krmutt
krmutt is a Kotlin library to generate random strings based on some sort of context-sensitive grammar.

The project is a reimplementation and reimagining of the original [rmutt](https://github.com/joefutrelle/rmutt), developed by Joe Frutelle.
It also aims to provide an API similar to the [JS port](https://github.com/rstuven/rmutt.js) created by rstuven.

Differently from both projects though, krmutt aims to support a different syntax, based around the Kotlin language and Kotlin Scripts, rather than a custom language.
This allows for IDE support without custom plugins (any IDE that can understand Kotlin can also understand krmutt scripts) and thus leads to a better user experience.
Support for the official language might be added at a later date.
