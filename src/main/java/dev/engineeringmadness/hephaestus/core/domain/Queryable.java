package dev.engineeringmadness.hephaestus.core.domain;

import lombok.Data;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Queryable {
    void paginate(Integer pageSize, Integer pageNumber);
    void sort(String column, SortDirection sortDirection);
    void initializeTable(Consumer<String> queryExecutor);
    void limit(Integer top);
}
