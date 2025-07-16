package dev.engineeringmadness.hephaestus.core.executions;

import dev.engineeringmadness.hephaestus.core.domain.AbstractQuery;
import dev.engineeringmadness.hephaestus.core.domain.QueryExecutionException;
import dev.engineeringmadness.hephaestus.core.domain.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class QueryExecutorService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<HashMap<String, Object>> queryRowMapper = new QueryRowMapper();

    public List<HashMap<String, Object>> executeQuery(AbstractQuery command, Integer pageSize, Integer pageNumber, String sortColumn, SortDirection sortDirection, Boolean aiQueryGeneration) {
        command.initializeTable(this::queryDispatcher);
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
