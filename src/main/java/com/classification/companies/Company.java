package com.classification.companies;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Company {
    @JsonProperty("Company ID")
    private int id;

    @JsonProperty("Company Name")
    private String name;
}
