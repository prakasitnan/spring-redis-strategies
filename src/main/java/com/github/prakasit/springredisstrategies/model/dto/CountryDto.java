package com.github.prakasit.springredisstrategies.model.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {

    private String countryId;
    private String countryName;
    private String countryAbbName;
    private String status;


}
