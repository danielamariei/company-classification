package com.classification.classifiers;

import com.classification.articles.Article;
import com.classification.companies.Company;

import java.util.List;

public class PatriciaStrategy implements Strategy {
    @Override
    public boolean isMentioned(Company company) {
        return false;
    }

    @Override
    public void index(List<Article> articles) {

    }
}
