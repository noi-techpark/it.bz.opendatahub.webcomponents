package it.bz.opendatahub.webcomponents.crawlerservice.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.OriginSystemEntry;
import it.bz.opendatahub.webcomponents.crawlerservice.exception.CrawlerException;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Date;

@Repository
public class SystemRepositoryImpl implements SystemRepository {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SystemRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String getHeadOfMasterOrigin() {
        String json;
        try {
            json = jdbcTemplate.queryForObject("SELECT data FROM system WHERE key='ORIGIN'", String.class);
        }
        catch (EmptyResultDataAccessException e) {
            jdbcTemplate.execute("INSERT INTO system (key) VALUES ('ORIGIN') ON CONFLICT DO NOTHING;");

            json = "{}";
        }

        OriginSystemEntry entry = createOriginSystemEntryFromJson(json);

        return entry.getHead();
    }

    @Override
    public void setHeadOfMasterOrigin(String commitHash) {
        OriginSystemEntry entry = new OriginSystemEntry();
        entry.setHead(commitHash);
        entry.setLastCheck(new Date());

        jdbcTemplate.update("UPDATE system SET data=CAST(? AS jsonb) WHERE key='ORIGIN'", objectToJson(entry));
    }

    private OriginSystemEntry createOriginSystemEntryFromJson(String json) {
        try {
            return objectMapper.readValue(json, OriginSystemEntry.class);
        }
        catch (IOException e) {
            throw new CrawlerException(e);
        }
    }

    private String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        }
        catch (IOException e) {
            throw new CrawlerException(e);
        }
    }
}
