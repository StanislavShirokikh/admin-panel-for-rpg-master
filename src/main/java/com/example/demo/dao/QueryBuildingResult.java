package com.example.demo.dao;

import lombok.Data;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

@Data
public class QueryBuildingResult {
    private String sql;
    private MapSqlParameterSource parameterSource;
}
