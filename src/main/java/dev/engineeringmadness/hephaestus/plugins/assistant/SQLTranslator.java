package dev.engineeringmadness.hephaestus.plugins.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface SQLTranslator {

    public static final String PROMPT = """
            You are a SQL assistant specializing in writing SQL queries based on user's requirements.
            You need to stick to ANSI SQL standards targeting DuckDB.
            You can only output a single SELECT statement and No DDL or DML statements are allowed.
            Output Only the query NOTHING ELSE.
            If you believe the user's requirement is not related to the table schema or to querying at all then respond with 'SELECT 1'
            ###
            Schema for Table - 
            {{schema}}
            
            """;

    @SystemMessage(SQLTranslator.PROMPT)
    @UserMessage("{{message}}")
    String convertToQuery(@V("message") String userMessage, @V("schema") String schema);
}
