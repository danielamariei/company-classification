package com.classification.transformers;

import com.classification.articles.Article;

import java.util.function.Function;

public abstract class Transformer implements Function<Article, Article> {
    public abstract Article apply(Article article);
}
