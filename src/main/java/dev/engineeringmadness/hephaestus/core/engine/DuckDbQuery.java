package dev.engineeringmadness.hephaestus.core.engine;

import dev.engineeringmadness.hephaestus.core.domain.AbstractQuery;
import dev.engineeringmadness.hephaestus.core.domain.QueryDto;
import dev.engineeringmadness.hephaestus.core.domain.SortDirection;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import java.util.function.Consumer;

@Data
public class DuckDbQuery extends AbstractQuery {

    @Override
    public void paginate(Integer pageSize, Integer pageNumber) {
        if(pageNumber != null && pageSize != null) {
            int calculatedOffset = (Math.max(pageNumber, 1)) * pageSize;
            this.setQuery(String.format("SELECT * FROM (%s) LIMIT %d OFFSET %d", this.getQuery(), pageSize, calculatedOffset));
        }
    }

    @Override
    public void sort(String column, SortDirection sortDirection) {
        if(StringUtils.isNotBlank(column) && sortDirection != null) {
            this.setQuery(String.format("SELECT * FROM (%s) ORDER BY %s %S", this.getQuery(), column, sortDirection.toString()));
        }
    }

    @Override
    public void initializeTable(Consumer<String> queryExecutor) {
        String createQuery = String.format("""
                CREATE TABLE IF NOT EXISTS %s AS
                    SELECT * FROM read_csv('%s');
                """, this.getAlias(), this.getSourceFile());
        queryExecutor.accept(createQuery);
    }

    @Override
    public void limit(Integer top) {
        if(top != null) {
            this.setQuery(String.format("SELECT * FROM (%s) LIMIT %d", this.getQuery(), top));
        }
    }

    public DuckDbQuery(QueryDto queryDto) {
        super(queryDto);
    }

}
