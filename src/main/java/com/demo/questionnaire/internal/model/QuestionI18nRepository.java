package com.demo.questionnaire.internal.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.demo.questionnaire.internal.model.QuestionI18n.QuestionI18nId;

public interface QuestionI18nRepository extends CrudRepository<QuestionI18n, QuestionI18nId> {

    public List<QuestionI18n> findByIdLanguageLocale(String locale);

    public List<QuestionI18n> findAllByIdQuestionId(Long questionId);

    public Optional<QuestionI18n> findOneByIdQuestionIdAndIdLanguageLocale(Long questionId, String locale);

}
