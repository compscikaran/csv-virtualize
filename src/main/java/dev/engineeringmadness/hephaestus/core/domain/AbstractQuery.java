package dev.engineeringmadness.hephaestus.core.domain;

import lombok.Data;

@Data
public abstract class AbstractQuery implements Queryable{
    private String query;
    private String sourceFile;
    private String alias;
}
