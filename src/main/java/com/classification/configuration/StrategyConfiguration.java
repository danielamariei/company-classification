package com.classification.configuration;

import com.classification.classifiers.*;
import com.classification.transformers.TransformerChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class StrategyConfiguration {

    @Bean
    @Profile("linearScanStrategy")
    public Strategy linearScanStrategy(TransformerChain transformerChain) {
        return new LinearScanStrategy(transformerChain);
    }

    @Bean
    @Profile("luceneStrategy")
    public Strategy luceneStrategy() {
        return new LuceneStrategy();
    }

    @Bean
    @Profile("trieStrategy")
    public Strategy trieStrategy() {
        return new TrieStrategy();
    }

    @Bean
    @Profile("patriciaStrategy")
    public Strategy patriciaStrategy() {
        return new PatriciaStrategy();
    }

}
