package com.classification.classifiers;

import com.classification.articles.Article;
import com.classification.articles.XmlArticleReader;
import com.classification.companies.Company;
import com.classification.companies.CsvCompanyReader;
import com.classification.transformers.IdentityTransformer;
import com.classification.transformers.TransformerChain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.annotation.Testable;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testable
class ClassifierTest {

    private static TransformerChain transformerChain;

    @BeforeAll
    public static void beforeAll(TestInfo testInfo) {
        transformerChain = new TransformerChain(Arrays.asList(new IdentityTransformer()));
    }

    private static List<Arguments> testCases() {
        return Arrays.asList(
                Arguments.of(new XmlArticleReader("src/test/resources/test-cases/apple/articles").read(), new CsvCompanyReader("src/test/resources/test-cases/apple/companies-to-search-for.csv").read(), new CsvCompanyReader("src/test/resources/test-cases/apple/expected-companies.csv").read()),
                Arguments.of(new XmlArticleReader("src/test/resources/test-cases/acer/articles").read(), new CsvCompanyReader("src/test/resources/test-cases/acer/companies-to-search-for.csv").read(), new CsvCompanyReader("src/test/resources/test-cases/acer/expected-companies.csv").read()),
                Arguments.of(new XmlArticleReader("src/test/resources/test-cases/microsoft/articles").read(), new CsvCompanyReader("src/test/resources/test-cases/microsoft/companies-to-search-for.csv").read(), new CsvCompanyReader("src/test/resources/test-cases/microsoft/expected-companies.csv").read()),
                Arguments.of(new XmlArticleReader("src/test/resources/test-cases/microsoftware/articles").read(), new CsvCompanyReader("src/test/resources/test-cases/microsoftware/companies-to-search-for.csv").read(), new CsvCompanyReader("src/test/resources/test-cases/microsoftware/expected-companies.csv").read())
        );
    }

    @ParameterizedTest
    @DisplayName("Test classification returns expected results for Lucene Strategy")
    @MethodSource("testCases")
    public void testClassificationReturnsExpectedResultsForLuceneStrategy(List<Article> articles, List<Company> companies, List<Company> expectedCompanies) {
        Classifier classifier = new Classifier(new LuceneStrategy());
        List<Company> actualCompanies = classifier.classify(articles, companies);
        assertThat(actualCompanies).hasSameElementsAs(expectedCompanies);
    }

    @ParameterizedTest
    @DisplayName("Test classification returns expected results for Linear Scan Strategy")
    @MethodSource("testCases")
    public void testClassificationReturnsExpectedResultsForLinearScanStrategy(List<Article> articles, List<Company> companies, List<Company> expectedCompanies) {
        Classifier classifier = new Classifier(new LinearScanStrategy(transformerChain));
        List<Company> actualCompanies = classifier.classify(articles, companies);
        assertThat(actualCompanies).hasSameElementsAs(expectedCompanies);
    }

    @Disabled("Not implemented")
    @ParameterizedTest
    @DisplayName("Test classification returns expected results for Patricia Strategy")
    @MethodSource("testCases")
    public void testClassificationReturnsExpectedResultsForPatriciaStrategy(List<Article> articles, List<Company> companies, List<Company> expectedCompanies) {
        Classifier classifier = new Classifier(new PatriciaStrategy());
        List<Company> actualCompanies = classifier.classify(articles, companies);
        assertThat(actualCompanies).hasSameElementsAs(expectedCompanies);
    }

    @Disabled("Not implemented")
    @ParameterizedTest
    @DisplayName("Test classification returns expected results for Trie Strategy")
    @MethodSource("testCases")
    public void testClassificationReturnsExpectedResultsForTrieStrategy(List<Article> articles, List<Company> companies, List<Company> expectedCompanies) {
        Classifier classifier = new Classifier(new TrieStrategy());
        List<Company> actualCompanies = classifier.classify(articles, companies);
        assertThat(actualCompanies).hasSameElementsAs(expectedCompanies);
    }

}