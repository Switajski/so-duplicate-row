package com.demo.questionnaire.internal.model;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    Optional<Question> findOneById(long id);

}
