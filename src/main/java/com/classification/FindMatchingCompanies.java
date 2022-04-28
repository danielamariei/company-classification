package com.classification;

import com.classification.articles.Article;
import com.classification.articles.ArticleReader;
import com.classification.classifiers.Classifier;
import com.classification.companies.Company;
import com.classification.companies.CompanyReader;
import com.classification.printer.Printer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

public class FindMatchingCompanies {

    @Autowired
    private ArticleReader articleReader;

    @Autowired
    private CompanyReader companyReader;

    @Autowired
    private Classifier companyClassifier;

    @Autowired
    private Printer printer;

    @PostConstruct
    public void printMatchingCompanies() {
        List<Article> articles = articleReader.read();
        List<Company> companies = companyReader.read();

        printer.print(companyClassifier.classify(articles, companies));
    }

}
