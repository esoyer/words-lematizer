# Purpose

This application takes a CSV file consisting of a single column of words with no header and a dictionary as inputs
and writes back a CSV file containing the lemmas of the given words using the Hunspell tool.

# Requirements

- JDK 8
- Maven 2 (ideally the latest release)
- A copy of a dictionary used by Hunspell corresponding to the words you are searching the lemmas for which can be 
downloaded [here](http://grammalecte.net/download/fr/hunspell-french-dictionaries-v7.0.zip) 
for example for the French version.

# How to use

1. Package the application as a fat jar by building this project with Maven using the `package` profile :
`mvn clean install -DskipTests -Ppackage`
Once this is done, you should have a jar in the `target/` directory named `words-lematizer-1.0-SNAPSHOT.jar`.


2. To launch the app, type :

`java -cp target/words-lematizer-1.0-SNAPSHOT.jar com.eso.wordslematizer.Application
--output-path [output path]
--words-path [words path]
--dict-path [dict path]
--affix-path [affix path]`

