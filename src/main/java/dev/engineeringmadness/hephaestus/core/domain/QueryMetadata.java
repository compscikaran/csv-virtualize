package dev.engineeringmadness.hephaestus.core.domain;

import lombok.Data;

@Data
public class QueryMetadata {
    public String query;
    public String sourceFile;
    public String alias;
}
