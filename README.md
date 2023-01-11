# Introduction
- Exploratory Java application for indexing a corpus of articles and searching companies in the corpus of articles using different strategies
- Exploring the use of Lucene for indexing text and searching the index using various Query techniques
- Fine-tuning for optimal results is required and is context-dependent
- Please take into consideration that this is a specific use-case and the same technique/approach can be used in other contexts (e.g., DNA sequencing, finding specific faults in sets of wafers to be inspected, etc.)

# Architecture 
- Input: corpus of articles + list of companies to search for
- Output: companies that can be found in the corpus of articles

```mermaid
flowchart LR
        A(Article Corpus) -->|index| CC(Company Classifier)
        C(Companies) -->|search| CC(Company Classifier)
        CC(Comp[any Classifier) -->|output| MC(Found Companies)
    
        IS(Indexing Strategy) --> CC(Company Classifier)
        LSS(Linear Scan Strategy) --> IS(Indexing Strategy)
        LS(Lucene Strategy) --> IS(Indexing Strategy)
        TS(Trie Strategy) --> IS(Indexing Strategy)
        PS(Patricia Strategy) --> IS(Indexing Strategy)
```

# Solution
### Technologies
- Spring
- Gradle
- Lucene

### Package Structure
- `articles`: logic for reading the corpus of articles
- `classifiers`:
  - Logic for finding companies in the corpus of text
  - Has several indexing strategies: Linear Scan, Lucene, Patricia, Trie
- `companies`: logic for reading companies
- `configuration`: bean creation
- `lucene`: abstraction for an In Memory Lucene Index
- `printer`: logic for printing the results
- `transformers`: logic for applying different types of transformers on the corpus of articles before indexing it

### Conceptual Approach
The central piece of the solution revolves around searching for companies in the corpus of articles in an efficient manner:
- The naive approach does a linear scan
- More complex and advanced approaches build an index from the corpus of articles and perform the search on this index to improve performance (both time and space-wise)
  - Trie
  - Patricia (reduces the storage requirements by compressing common paths)
  - Lucene (uses an In Memory Lucene Index)

Current implementations:
- Linear Scan Strategy
- Lucene Strategy

Future directions:
- Trie Strategy
- Patricia Strategy

### Linear Scan
Does a linear scan over the entire corpus of articles and tries to find a match for each company.

### Trie 
These method needs careful analysis of the input companies to determine the max length of the words that should be stored in the data structures. 
There are two approaches:
1. Number of characters: determine the maximum length of a company name and cap the length of sequence of terms to that. 
   - Example: maxLength(Shockley Semiconductor Laboratory) = 34
2. Number of terms: determine the maximum length of terms an company name consists of and cap the length of the sequence of terms to that.
   - Example: maxTerms(Shockley Semiconductor Laboratory) = 3

### Patricia
The same as for the `Trie` Strategy applies. Space usage is more efficient int this case.

### Lucene
Lucene does most of the work out of the box for us: creating an efficient indexing structure, splitting, lowercase conversion, punctuation removal, etc.
 
# Testing
- Parameterized tests for each test cases and for each strategy

# Prerequisites
- Java 11 is available on your system (tested with Java 11 and Java 18)
- Make sure that the CSV file for the input companies to search for has the following header names (please rename the CSV file header names if required):
  - `Company ID`
  - `Company Name`

# Running the application

### Params
- The corpus of articles: initialize `articles.path` with the path to the article corpus directory. Escape the path if required. 
- The companies to search for: initialize `companies.path` with the path to the companies file. Escape the path if required.
- There will be multiple `spring.profiles.active` properties:
  - Indexing strategy: initialize `spring.profiles.active` with one of `[linearScanStrategy, luceneStrategy, trieStrategy, patriciaStrategy]`. 
    - Current implementations only for `linearScanStrategy` and `luceneStrategy`.
    - Expect running times around `minutes, 10s of minutes, or even hours` for `linearScanStrategy`, depending on the size of you corpus and hardware configuration
    - Expect running times around `seconds to 10s of seconds` for `luceneStrategy`, depending on the size of your corpus and hardware configuration
  - Printing the found companies: initialize `spring.profiles.active` with one of `[idPrinter, idAndNamePrinter]`

### CMD

```groovy
./gradlew -q bootRun -Pargs=--articles.path=<articles-directory-path>,--companies.path=<companies-file-path>,--spring.profiles.active=luceneStrategy,--spring.profiles.active=idAndNamePrinter
```

You can pipe the execution to a file, if you want to store the results for later analysis.

### Notes
- This should also work under Windows using Git Bash. Otherwise, use the `gradlew.bat` version for pure Windows systems.
- If you also want to see the running time, add `time` before the previous command, if available on your system.

### Errors
- Please consult the contents of the `application.log` file if problems arise. This can be found in the root directory.

# Future directions
- **Trie**: the structure for this work is present, but the approach needs to be implemented
- **Patricia**: the structure for this work is present, but the approach needs to be implemented
- **TransformerChain**: uses an implicit Identity transformer. More complex transformers can be added and be used on without altering existing code.
- **Company Names (Aliases/Synonyms)**: 
  - The companies might have multiple aliases/synonyms that refer to the same company/thing/concept. 
  - Work that identifies these from a company name and stores them so we can search for any alias/synonym needs to be done. 
  - This would be done as part of reading the company list. 
  - After this, a search would be done for every alias/synonym and a hit generated when at least one of the names matches.
- **Injection**: Some fields were injected with @Autowired. This should be replaced with Constructor Injection
- **Precision/Recall**
  - The current implementation for Lucene Strategy uses a `PhraseQuery` for searching a company in the index.
  - This matches documents containing a particular sequence of terms and uses a slop factor to determine how many positions may occur between any two terms in the phrase and still be considered a match: https://lucene.apache.org/core/9_1_0/core/org/apache/lucene/search/package-summary.htm 
  - This can further be improved by incorporating Fuzzy Queries that use Levenshtein distance: https://en.wikipedia.org/wiki/Levenshtein_distance

