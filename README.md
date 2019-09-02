# GeneratePatterns

This module is used to generate the patterns with the maximum pattern length to be 3. The algorithm that used to generate these patterns is mentioned in the paper “Ontology Enrichment by Discovering Multi-Relational Association Rules from Ontological Knowledge Bases”

- The module's input is located in the function “main” of the file: PredictingAssertionsProject.java
- The module's output is files *.txt containing discovered SWRL rules. However, some of these SWRL rules are able to be redundant rules (rules that are inconsistent when considered jointly with the reference ontology). In order to eliminate these redundant rules, we use a module in this address:
https://github.com/tdminh2110/CheckPatternMultiThreading
