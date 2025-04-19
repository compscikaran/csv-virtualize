package dev.engineeringmadness.hephaestus.plugins.assistant;

import dev.engineeringmadness.hephaestus.core.domain.AbstractQuery;
import dev.engineeringmadness.hephaestus.core.domain.QueryPlugin;
import dev.engineeringmadness.hephaestus.core.executions.QueryRowMapper;
import dev.engineeringmadness.hephaestus.plugins.assistant.SQLTranslator;
import dev.langchain4j.model.anthropic.AnthropicChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Lazy
@Service("QueryGenerator")
public class AIAssistantPlugin implements QueryPlugin {

    public static final String CLAUDE_3_5 = "claude-3-5-haiku-20241022";
    private RowMapper<HashMap<String, Object>> queryRowMapper = new QueryRowMapper();

    @Value("${claude.key}")
    private String apiKey;

    private SQLTranslator translator;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        AnthropicChatModel model = AnthropicChatModel.builder()
                .apiKey(apiKey)
                .modelName(CLAUDE_3_5)
                .build();

        this.translator = AiServices.create(SQLTranslator.class, model);
    }

    public String createQuery(String schema, String message) {
        return this.translator.convertToQuery(message, schema);
    }

    public String getSchemaForTable(String alias) {
        String reflectSql = String.format("SELECT * FROM information_schema.columns WHERE table_name = '%s'", alias);
        List<HashMap<String, Object>> tableDetails = jdbcTemplate.query(reflectSql, queryRowMapper);
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Table name: %s \n", alias));
        for(var row: tableDetails) {
            builder.append("Column name: ");
            builder.append(row.get("column_name"));
            builder.append(" Data type: ");
            builder.append(row.get("data_type"));
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public AbstractQuery execute(AbstractQuery query) {
        String generatedSql = this.createQuery(query.getQuery(), getSchemaForTable(query.getAlias()));
        query.setQuery(generatedSql);
        return query;
    }
}
