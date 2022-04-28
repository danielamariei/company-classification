package com.classification.configuration;

import com.classification.FindMatchingCompanies;
import com.classification.articles.ArticleReader;
import com.classification.articles.XmlArticleReader;
import com.classification.classifiers.Classifier;
import com.classification.classifiers.Strategy;
import com.classification.companies.CompanyReader;
import com.classification.companies.CsvCompanyReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClassificationConfiguration {

    @Value("${articles.path}")
    private String articlesPath;

    @Value("${companies.path}")
    private String companiesPath;

    @Bean
    public ArticleReader articleReader() {
        return new XmlArticleReader(articlesPath);
    }

    @Bean
    public CompanyReader companyReader() {
        return new CsvCompanyReader(companiesPath);
    }

    @Bean
    public Classifier companyClassifier(Strategy strategy) {
        return new Classifier(strategy);
    }

    @Bean
    public FindMatchingCompanies findMatchingCompanies() {
        return new FindMatchingCompanies();
    }
}
