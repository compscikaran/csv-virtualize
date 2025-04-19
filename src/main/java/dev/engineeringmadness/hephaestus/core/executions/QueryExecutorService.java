package dev.engineeringmadness.hephaestus.core.executions;

import dev.engineeringmadness.hephaestus.core.domain.AbstractQuery;
import dev.engineeringmadness.hephaestus.core.domain.QueryPlugin;
import dev.engineeringmadness.hephaestus.core.domain.SortDirection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class QueryExecutorService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    private RowMapper<HashMap<String, Object>> queryRowMapper = new QueryRowMapper();

    public List<HashMap<String, Object>> executeQuery(AbstractQuery command, Integer pageSize, Integer pageNumber, String sortColumn, SortDirection sortDirection, String plugin) {
        command.initializeTable(this::queryDispatcher);

        if(StringUtils.isNotBlank(plugin)) {
            QueryPlugin operator = applicationContext.getBean(plugin, QueryPlugin.class);
            command = operator.execute(command);
        }

        command.sort(sortColumn, sortDirection);
        command.paginate(pageSize, pageNumber);
        return jdbcTemplate.query(command.getQuery(), queryRowMapper);
    }

    private void queryDispatcher(String query) {
        jdbcTemplate.execute(query);
    }
}
