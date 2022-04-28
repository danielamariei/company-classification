package com.classification.companies;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;

public class CsvCompanyReader implements CompanyReader {

    private static final Logger logger = LoggerFactory.getLogger(CsvCompanyReader.class);

    private final String path;

    public CsvCompanyReader(String path) {
        this.path = path;
    }

    @Override
    public List<Company> read() {
        ObjectReader objectReader = getCsvObjectReader();

        try (Reader reader = new FileReader(path)) {
            return objectReader.<Company>readValues(reader).readAll();
        } catch (IOException e) {
            logger.warn("Problems while trying to read the companies from the CSV file: {}", e);
        }

        return Collections.emptyList();
    }

    private ObjectReader getCsvObjectReader() {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';');
        return csvMapper.readerFor(Company.class).with(schema);
    }
}
