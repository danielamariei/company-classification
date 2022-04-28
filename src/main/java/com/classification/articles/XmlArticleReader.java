package com.classification.articles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class XmlArticleReader implements ArticleReader {

    private static final Logger logger = LoggerFactory.getLogger(XmlArticleReader.class);

    private ObjectMapper objectMapper = new XmlMapper();
    private final String path;

    public XmlArticleReader(String path) {
        this.path = path;
    }

    @Override
    @SneakyThrows
    public List<Article> read() {
        return Files
                .walk(Paths.get(path))
                .parallel()
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .map(xmlArticle -> toJavaArticle(xmlArticle))
                .filter(optionalArticle -> optionalArticle.isPresent())
                .map(optionalArticle -> optionalArticle.get())
                .collect(Collectors.toList());
    }

    private Optional<Article> toJavaArticle(File xmlArticle) {
        try {
            return Optional.of(objectMapper.readValue(xmlArticle, Article.class));
        } catch (IOException e) {
            logger.warn("The following xml file does not have the expected structure: {}", xmlArticle.getPath());
        }

        return Optional.empty();
    }
}
