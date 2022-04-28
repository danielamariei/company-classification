package com.classification.lucene;

import lombok.SneakyThrows;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;

import java.util.Collection;

public class InMemoryLuceneIndex {

    private Directory index = new ByteBuffersDirectory();

    @SneakyThrows
    public void add(Document document) {
        IndexWriter indexWriter = new IndexWriter(index, new IndexWriterConfig());
        indexWriter.addDocument(document);
        indexWriter.close();
    }

    @SneakyThrows
    public void add(Collection<Document> documents) {
        IndexWriter indexWriter = new IndexWriter(index, new IndexWriterConfig());
        indexWriter.addDocuments(documents);
        indexWriter.close();
    }

    @SneakyThrows
    public TopDocs search(Query query, int maxHits) {
        IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(index));
        return indexSearcher.search(query, maxHits);
    }

    @SneakyThrows
    public void clear() {
        IndexWriter indexWriter = new IndexWriter(index, new IndexWriterConfig());
        indexWriter.deleteAll();
        indexWriter.close();
    }
}
