package dev.engineeringmadness.hephaestus.core.executions;

import dev.engineeringmadness.hephaestus.core.domain.AbstractQuery;
import dev.engineeringmadness.hephaestus.core.domain.QueryExecutionException;
import dev.engineeringmadness.hephaestus.core.domain.QueryPlugin;
import dev.engineeringmadness.hephaestus.core.domain.SortDirection;
import dev.engineeringmadness.hephaestus.plugins.PluginExecutionException;
import dev.engineeringmadness.hephaestus.plugins.validator.QueryValidationException;
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

        try {
            if (StringUtils.isNotBlank(plugin)) {
                QueryPlugin operator = applicationContext.getBean(plugin, QueryPlugin.class);
                command = operator.execute(command);
            }
        } catch (Exception ex) {
            if(ex instanceof QueryValidationException) {
                throw ex;
            }
            throw new PluginExecutionException(ex);
        }

        command.sort(sortColumn, sortDirection);
        command.paginate(pageSize, pageNumber);

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
