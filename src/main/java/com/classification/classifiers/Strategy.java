package com.classification.classifiers;

import com.classification.articles.Article;
import com.classification.companies.Company;

import java.util.List;

public interface Strategy {
    boolean isMentioned(Company company);

    void index(List<Article> articles);
}
