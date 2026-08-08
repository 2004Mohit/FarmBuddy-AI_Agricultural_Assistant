package org.pm.backendspringai.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VectorStoreRepository {

    private JdbcTemplate jdbcTemplate;

    public VectorStoreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isEmpty() {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM vector_store",
                Integer.class
        );

        return count != null && count == 0;
    }
}
