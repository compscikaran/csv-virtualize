package dev.engineeringmadness.hephaestus.plugins.validator;

import dev.engineeringmadness.hephaestus.core.domain.AbstractQuery;
import dev.engineeringmadness.hephaestus.core.domain.QueryPlugin;
import dev.engineeringmadness.hephaestus.plugins.validator.SQLValidator;
import dev.langchain4j.model.anthropic.AnthropicChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service("QueryValidator")
@Lazy
public class AIValidatorPlugin implements QueryPlugin {

    public static final String CLAUDE_3_5 = "claude-3-5-haiku-20241022";

    @Value("${claude.key}")
    private String apiKey;

    private SQLValidator validator;

    @PostConstruct
    public void init() {
        AnthropicChatModel model = AnthropicChatModel.builder()
                .apiKey(apiKey)
                .modelName(CLAUDE_3_5)
                .build();

        this.validator = AiServices.create(SQLValidator.class, model);
    }


    @Override
    public AbstractQuery execute(AbstractQuery query) {
        boolean isValidQuery = this.validator.validateQuery(query.getQuery());
        if(!isValidQuery) {
            throw new QueryValidationException("Supplied query is not valid");
        }
        return query;
    }
}
