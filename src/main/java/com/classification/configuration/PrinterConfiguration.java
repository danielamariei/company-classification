package com.classification.configuration;

import com.classification.printer.IdAndNamePrinter;
import com.classification.printer.IdPrinter;
import com.classification.printer.Printer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class PrinterConfiguration {

    @Bean
    @Profile("idPrinter")
    public Printer idPrinter() {
        return new IdPrinter();
    }

    @Bean
    @Profile("idAndNamePrinter")
    public Printer idAndNamePrinter() {
        return new IdAndNamePrinter();
    }
}
