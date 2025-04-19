package dev.engineeringmadness.hephaestus.core.engine;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.engineeringmadness.hephaestus.core.domain.AbstractQuery;
import dev.engineeringmadness.hephaestus.core.domain.QueryDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class EngineConfig {

    @Value("${hephaestus.engine}")
    private String engine;

    @Bean
    public DataSource dataSource() {
        switch (engine) {
            case "duckdb" -> {
                HikariConfig config = new HikariConfig();
                config.setDriverClassName("org.duckdb.DuckDBDriver");
                config.setMaximumPoolSize(10);
                config.setMaxLifetime(3);
                config.setJdbcUrl("jdbc:duckdb:hephaestusdb");
                HikariDataSource ds = new HikariDataSource(config);
                return ds;
            }
            default -> throw new IllegalArgumentException("Invalid Engine configuration");
        }

    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource);
        return template;
    }

    public AbstractQuery createQueryCommand(QueryDto dto) {
        switch (engine) {
            case "duckdb" -> {
                return new DuckDbQuery(dto);
            }
            default -> throw new IllegalArgumentException("Invalid Engine configuration");
        }
    }
}
