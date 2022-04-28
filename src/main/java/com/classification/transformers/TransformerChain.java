package com.classification.transformers;

import com.classification.articles.Article;

import java.util.List;

public class TransformerChain extends Transformer {
    private List<Transformer> transformers;

    public TransformerChain(List<Transformer> transformers) {
        this.transformers = transformers;
    }

    @Override
    public Article apply(Article article) {
        for (Transformer transformer : transformers) {
            article = transformer.apply(article);
        }

        return article;
    }
}
