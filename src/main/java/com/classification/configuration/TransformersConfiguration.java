package com.classification.configuration;

import com.classification.transformers.IdentityTransformer;
import com.classification.transformers.Transformer;
import com.classification.transformers.TransformerChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TransformersConfiguration {

    @Bean
    public TransformerChain transformerChain(List<Transformer> transformers) {
        return new TransformerChain(transformers);
    }

    @Bean
    public Transformer identityTransformer() {
        return new IdentityTransformer();
    }
}
