package dev.engineeringmadness.hephaestus.core.duckdb;

import dev.engineeringmadness.hephaestus.core.domain.AbstractQuery;
import dev.engineeringmadness.hephaestus.core.domain.SortDirection;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import java.util.function.Consumer;

@Data
public class DuckDbQuery extends AbstractQuery {

    public void paginate(Integer pageSize, Integer pageNumber) {
        if(pageNumber != null && pageSize != null) {
            int calculatedOffset = (Math.max(pageNumber, 1)) * pageSize;
            this.setQuery(String.format("SELECT * FROM (%s) LIMIT %d OFFSET %d", this.query, pageSize, calculatedOffset));
        }
    }

    @Override
    public void sort(String column, SortDirection sortDirection) {
        if(StringUtils.isNotBlank(column) && sortDirection != null) {
            this.setQuery(String.format("SELECT * FROM (%s) ORDER BY %s %S", this.query, column, sortDirection.toString()));
        }
    }

    @Override
    public void initializeTable(Consumer<String> queryExecutor) {
        String createQuery = String.format("""
                CREATE TABLE IF NOT EXISTS %s AS
                    SELECT * FROM read_csv('%s');
                """, this.alias, this.sourceFile);
        queryExecutor.accept(createQuery);
    }

}