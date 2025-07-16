package dev.engineeringmadness.hephaestus.interactors;

import dev.engineeringmadness.hephaestus.core.domain.QueryDto;
import dev.engineeringmadness.hephaestus.core.domain.QueryResult;
import dev.engineeringmadness.hephaestus.core.domain.SliceParameters;
import dev.engineeringmadness.hephaestus.core.domain.SortDirection;
import dev.engineeringmadness.hephaestus.core.engine.DuckDbQuery;
import dev.engineeringmadness.hephaestus.core.engine.EngineConfig;
import dev.engineeringmadness.hephaestus.core.executions.QueryExecutorService;
import dev.engineeringmadness.hephaestus.core.executions.ResultUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class MainController {

    public static final String ENDPOINT = "/api/v1/query";

    @Autowired
    private QueryExecutorService queryExecutorService;

    @Autowired
    private EngineConfig engineConfig;

    @PostMapping(ENDPOINT)
    public ResponseEntity<QueryResult> getResults(@RequestBody QueryDto dto, HttpServletRequest request) {
        DuckDbQuery command = new DuckDbQuery(dto);
        SliceParameters parameters = new SliceParameters(request);
        List<HashMap<String, Object>> data = queryExecutorService.executeQuery(command, dto, parameters);
        if(data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new QueryResult(data, ResultUtils.generateNextPageLink(parameters, MainController.ENDPOINT)));
    }
}
