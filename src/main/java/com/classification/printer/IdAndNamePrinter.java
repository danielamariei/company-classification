package com.classification.printer;

import com.classification.companies.Company;

import java.util.List;

public class IdAndNamePrinter implements Printer {
    @Override
    public void print(List<Company> companies) {
        System.out.println("Company ID, Company Name");
        for (Company company : companies) {
            System.out.println(company.getId() + "," + company.getName());
        }

        System.out.flush();
    }
}
