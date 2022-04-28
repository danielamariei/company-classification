package com.classification.transformers;

import com.classification.articles.Article;

public class IdentityTransformer extends Transformer {
    @Override
    public Article apply(Article article) {
        return article;
    }
}
