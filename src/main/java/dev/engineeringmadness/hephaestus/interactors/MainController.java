package dev.engineeringmadness.hephaestus.interactors;

import dev.engineeringmadness.hephaestus.core.domain.AbstractQuery;
import dev.engineeringmadness.hephaestus.core.domain.QueryDto;
import dev.engineeringmadness.hephaestus.core.domain.SortDirection;
import dev.engineeringmadness.hephaestus.core.engine.EngineConfig;
import dev.engineeringmadness.hephaestus.core.executions.QueryExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    private QueryExecutorService queryExecutorService;

    @Autowired
    private EngineConfig engineConfig;

    @PostMapping("/query")
    public ResponseEntity<List<HashMap<String, Object>>> getResults(@RequestBody QueryDto dto,
                                                                   @RequestParam(required = false) Integer pageSize,
                                                                   @RequestParam(required = false) Integer pageNumber,
                                                                   @RequestParam(required = false) String sortColumn,
                                                                   @RequestParam(required = false) SortDirection sortDirection,
                                                                   @RequestParam(required = false) String plugin) {
        AbstractQuery command = engineConfig.createQueryCommand(dto);
        List<HashMap<String, Object>> data = queryExecutorService.executeQuery(command, pageSize, pageNumber, sortColumn, sortDirection, plugin);
        if(data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }
}
