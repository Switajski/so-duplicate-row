package com.demo.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.questionnaire.internal.model.questiontypes.MultipleChoiceConfig;
import com.demo.questionnaire.internal.model.questiontypes.Option;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.demo.i18n.model.Language;
import com.demo.i18n.model.LanguageRepository;
import com.demo.i18n.model.TranslationsDTO;
import com.demo.questionnaire.internal.model.Question;
import com.demo.questionnaire.internal.model.QuestionI18n;
import com.demo.questionnaire.internal.model.QuestionI18n.QuestionI18nId;
import com.demo.questionnaire.internal.model.QuestionI18nRepository;
import com.demo.questionnaire.internal.model.QuestionRepository;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

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
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionI18nRepository questionI18nRepo;
    @Autowired
    private LanguageRepository langRepository;
    @Autowired
    private EntityManager em;

    @Autowired
    protected Flyway flyway;

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

        langRepository.save(new Language(locale));

        final var config = new MultipleChoiceConfig();
        config.getOptions().add(new Option(config));
        config.getOptions().add(new Option(config));
        final var q = questionRepository.save(new Question(config));
        assertThat(q.getId()).isEqualTo(questionId);

        final var lang = langRepository.findOneByLocale(locale).orElseThrow();
        final var id = new QuestionI18nId(em.getReference(Question.class, questionId), lang);

        questionI18nRepo.save(new QuestionI18n(
                id,
                ""
        ));

        QuestionI18n trans = questionI18nRepo.findOneByIdQuestionIdAndIdLanguageLocale(questionId, locale)
                .orElse(null);
        assertThat(trans).isNotNull();

        var byIds = questionI18nRepo.findAllById(Set.of(id));
        assertThat(byIds).hasSize(1);
        questionI18nRepo.findById(id);
    }
}
