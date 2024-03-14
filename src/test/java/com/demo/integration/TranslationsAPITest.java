package com.demo.integration;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "management.port=-1",
        "spring.flyway.clean-disabled=false"
})
@Slf4j
@ActiveProfiles(profiles = "test", resolver = AdditionalActiveProfilesResolver.class)
public class TranslationsAPITest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected Flyway flyway;
    @Autowired
    AService service;

    @BeforeEach
    public void cleanDb() {
        log.info("Enforcing a clean DB ...");
        // while it is costly (timewise) to always re-create a clean DB, it enforces deterministic test context.
        flyway.clean();
        flyway.migrate();
        log.info("done");
    }

    @Test
    public void deleteQuestionI18n() throws Exception {
        Long questionId = 2L;
        String locale = "en";

        service.oneTransation(locale, questionId);
    }
}
