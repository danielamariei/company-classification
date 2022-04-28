package com.classification.classifiers;

import com.classification.articles.Article;
import com.classification.articles.ArticleReader;
import com.classification.companies.Company;

import java.util.List;
import java.util.stream.Collectors;

public class Classifier {

    private Strategy strategy;

    private ArticleReader articleReader;

    public Classifier(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<Company> classify(List<Article> articles, List<Company> companies) {
        strategy.index(articles);
        return companies.parallelStream().filter(strategy::isMentioned).collect(Collectors.toList());
    }

}
