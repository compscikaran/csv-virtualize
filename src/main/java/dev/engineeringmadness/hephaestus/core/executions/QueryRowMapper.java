package dev.engineeringmadness.hephaestus.core.executions;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class QueryRowMapper implements RowMapper<HashMap<String, Object>> {

    @Override
    public HashMap<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        HashMap<String, Object> result = new HashMap<>();
        ResultSetMetaData columnMetadata = rs.getMetaData();
        for(int i = 1; i <= columnMetadata.getColumnCount(); i++) {
            String columnName = columnMetadata.getColumnName(i);
            Object data = rs.getObject(columnName);
            result.put(columnName, data);
        }
        return result;
    }
}
