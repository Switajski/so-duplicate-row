package com.demo.i18n.model;

import java.io.Serializable;
import java.util.Map;

import com.demo.questionnaire.internal.model.QuestionI18n;
import com.demo.questionnaire.internal.model.questiontypes.OptionI18n;

import lombok.Builder;
import lombok.Data;

/**
 * Maps the ids of a {@link TranslatedEntity} to the respective {@link I18nEntityDTO}. A {@link TranslationsDTO} is
 * related to a single {@link Language} (not being part of the DTO). From this, a {@link I18nFactory} can create a
 * {@link I18nEntity} to store the translation connected to (a) a {@link Language} and (b) the {@link TranslatedEntity}
 * by using the respective {@link I18nId}.
 *
 
 */
@Builder
@Data
public class TranslationsDTO implements Serializable { // Serializable for caching
    private static final long serialVersionUID = 1L;
    private Map<String, OptionI18n.DTO> options;
    private Map<String, QuestionI18n.DTO> questions;
}
