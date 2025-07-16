package dev.engineeringmadness.hephaestus.core.executions;

import ch.qos.logback.core.util.StringUtil;
import dev.engineeringmadness.hephaestus.core.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueryExecutorService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<HashMap<String, Object>> queryRowMapper = new QueryRowMapper();

    public List<HashMap<String, Object>> executeQuery(AbstractQuery command, QueryDto dto, SliceParameters params) {
        // create table in DuckDb based on input CSV
        command.initializeTable(this::queryDispatcher);

        // Apply slicing criteria
        if(StringUtils.isNotBlank(params.getSortColumn()) && params.getSortDirection() != null)
            command.sort(params.getSortColumn(), params.getSortDirection());
        if(params.getPageSize() != null && params.getPageNumber() != null)
            command.paginate(params.getPageSize(), params.getPageNumber());
        if(params.getTop() != null)
            command.limit(params.getTop());

        // Execute query and return results
        try {
            return jdbcTemplate.query(command.getQuery(), queryRowMapper);
        } catch (Exception ex) {
            throw new QueryExecutionException(ex);
        }
    }

    private void queryDispatcher(String query) {
        jdbcTemplate.execute(query);
    }
}
