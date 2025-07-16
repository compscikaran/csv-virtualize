package dev.engineeringmadness.hephaestus.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
public class QueryResult {

    public List<HashMap<String, Object>> data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String nextPage;
}
