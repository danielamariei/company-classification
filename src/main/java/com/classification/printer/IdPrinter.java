package com.classification.printer;

import com.classification.companies.Company;

import java.util.List;

public class IdPrinter implements Printer {
    @Override
    public void print(List<Company> companies) {
        System.out.println("Company ID");
        companies.stream().map(company -> company.getId()).forEach(System.out::println);
        System.out.flush();
    }
}
