package dev.engineeringmadness.hephaestus.core.domain;

import lombok.Data;

@Data
public class QueryDto {
    private String query;
    private String sourceFile;
    private String alias;
}