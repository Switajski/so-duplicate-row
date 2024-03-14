package com.demo.integration;

import com.demo.i18n.model.Language;
import com.demo.i18n.model.LanguageRepository;
import com.demo.questionnaire.internal.model.Question;
import com.demo.questionnaire.internal.model.QuestionI18n;
import com.demo.questionnaire.internal.model.QuestionI18nRepository;
import com.demo.questionnaire.internal.model.QuestionRepository;
import com.demo.questionnaire.internal.model.questiontypes.MultipleChoiceConfig;
import com.demo.questionnaire.internal.model.questiontypes.Option;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Service
@Transactional
public class AService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionI18nRepository questionI18nRepo;
    @Autowired
    private LanguageRepository langRepository;
    @Autowired
    private EntityManager em;

    public void oneTransation(String locale, Long questionId) {
        langRepository.save(new Language(locale));

        final var config = new MultipleChoiceConfig();
        config.getOptions().add(new Option(config));
        config.getOptions().add(new Option(config));
        final var q = questionRepository.save(new Question(config));
        assertThat(q.getId()).isEqualTo(questionId);

        final var lang = langRepository.findOneByLocale(locale).orElseThrow();
        final var id = new QuestionI18n.QuestionI18nId(em.getReference(Question.class, questionId), lang);

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
