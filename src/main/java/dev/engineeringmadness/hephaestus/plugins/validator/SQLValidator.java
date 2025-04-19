package dev.engineeringmadness.hephaestus.plugins.validator;

import dev.langchain4j.service.SystemMessage;

public interface SQLValidator {

    public static final String PROMPT = """
            You are a SQL assistant specializing in validating SQL queries provided to you.
            You need to stick to ANSI SQL standards targeting DuckDB.
            Given the query judge whether it will execute correctly on DuckDB provided the table used exists
            """;

    @SystemMessage(SQLValidator.PROMPT)
    boolean validateQuery(String userMessage);
}
