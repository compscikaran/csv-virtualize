package dev.engineeringmadness.hephaestus.interactors;

import dev.engineeringmadness.hephaestus.core.duckdb.DuckDbQuery;
import dev.engineeringmadness.hephaestus.core.domain.SortDirection;
import dev.engineeringmadness.hephaestus.core.executions.QueryExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    private QueryExecutorService queryExecutorService;

    @PostMapping("/query")
    public List<HashMap<String, Object>> getResults(@RequestBody DuckDbQuery command,
                                                    @RequestParam(required = false) Integer pageSize,
                                                    @RequestParam(required = false) Integer pageNumber,
                                                    @RequestParam(required = false) String sortColumn,
                                                    @RequestParam(required = false) SortDirection sortDirection,
                                                    @RequestParam(required = false) String plugin) {
        return queryExecutorService.executeQuery(command, pageSize, pageNumber, sortColumn, sortDirection, plugin);
    }
}
