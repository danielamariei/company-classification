package com.classification.classifiers;

import com.classification.articles.Article;
import com.classification.companies.Company;
import com.classification.transformers.TransformerChain;

import java.util.List;
import java.util.stream.Collectors;

public class LinearScanStrategy implements Strategy {

    private List<Article> articles;

    private TransformerChain transformerChain;

    public LinearScanStrategy(TransformerChain transformerChain) {
        this.transformerChain = transformerChain;
    }

    @Override
    public boolean isMentioned(Company company) {
        return articles.parallelStream().filter(article -> isMentioned(company, article)).findAny().isPresent();
    }

    private boolean isMentioned(Company company, Article article) {
        return article.getTitle().contains(company.getName()) || article.getText().contains(company.getName());
    }

    @Override
    public void index(List<Article> articles) {
        // Pass the articles through the transformer chain before indexing them
        // This might remove punctuation, transform to lowercase, and other relevant operations that could alter the text before the indexing operation
        this.articles = articles.parallelStream().map(transformerChain::apply).collect(Collectors.toList());
    }
}
