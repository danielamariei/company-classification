package com.classification.classifiers;

import com.classification.articles.Article;
import com.classification.companies.Company;
import com.classification.lucene.InMemoryLuceneIndex;
import lombok.SneakyThrows;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.lucene.search.BooleanClause.Occur;

public class LuceneStrategy implements Strategy {


    private static final String TITLE_FIELD = "title";
    private static final String TEXT_FIELD = "text";
    private InMemoryLuceneIndex inMemoryLuceneIndex = new InMemoryLuceneIndex();

    public LuceneStrategy() {
    }

    @SneakyThrows
    @Override
    public boolean isMentioned(Company company) {
        Query titleQuery = new PhraseQuery(3, TITLE_FIELD, company.getName().toLowerCase().split(" "));
        Query textQuery = new PhraseQuery(3, TEXT_FIELD, company.getName().toLowerCase().split(" "));

        // The company should occur in the title or the text
        BooleanQuery query = new BooleanQuery.Builder().add(titleQuery, Occur.SHOULD).add(textQuery, Occur.SHOULD).build();

        return wereMatchesFound(inMemoryLuceneIndex.search(query, 1));
    }

    @SneakyThrows
    @Override
    public void index(List<Article> articles) {
        inMemoryLuceneIndex.add(articles.parallelStream().map(this::toIndexableDocument).collect(Collectors.toList()));
    }

    @SneakyThrows
    private Document toIndexableDocument(Article article) {
        Document document = new Document();

        document.add(new TextField(TITLE_FIELD, article.getTitle(), Field.Store.NO));
        document.add(new TextField(TEXT_FIELD, article.getText(), Field.Store.NO));

        return document;
    }

    private boolean wereMatchesFound(TopDocs search) {
        return search.totalHits.value > 0;
    }
}
