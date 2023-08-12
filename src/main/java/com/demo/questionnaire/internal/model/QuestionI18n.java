package com.demo.questionnaire.internal.model;

import java.util.Optional;

import com.demo.i18n.model.AbstractI18nId;
import com.demo.i18n.model.I18nEntity;
import com.demo.i18n.model.I18nEntityDTO;
import com.demo.i18n.model.I18nFactory;
import com.demo.i18n.model.I18nIdFactory;
import com.demo.i18n.model.Language;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "question_i18n")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor // JPA
public class QuestionI18n implements I18nEntity<QuestionI18n.DTO> {
    @EmbeddedId
    private QuestionI18nId id;

    @Setter
    @NotNull
    @Column(nullable = false)
    private String text;


    @AllArgsConstructor
    @Getter
    // NOTE: a request only sends a single field set at a time!
    public static class DTO implements I18nEntityDTO<Question, DTO, QuestionI18n, QuestionI18nId> {
        private static final long serialVersionUID = 1L;
        private final String text;

        @Override
        public I18nIdFactory<Question, QuestionI18nId> getIdFactory() {
            return ID_FACTORY;
        }

        @Override
        public I18nFactory<DTO, QuestionI18n, QuestionI18nId> getI18nFactory() {
            return I18N_FACTORY;
        }
    }

    private final static I18nIdFactory<Question, QuestionI18nId> ID_FACTORY = (Language lang,
                                                                               Question entity) -> new QuestionI18nId(entity,
            lang);

    private final static I18nFactory<DTO, QuestionI18n, QuestionI18nId> I18N_FACTORY = (QuestionI18nId id,
                                                                                        Optional<QuestionI18n> oi18n,
                                                                                        DTO dto) -> {
        // existing or default
        QuestionI18n i = oi18n.orElse(new QuestionI18n(id, ""));
        i.setText(dto.getText());
        return i;
    };

    @Embeddable
    @Getter
    @NoArgsConstructor // JPA
    public static class QuestionI18nId extends AbstractI18nId<Question, QuestionI18nId> {
        private static final long serialVersionUID = 2898387059894851083L;

        @ManyToOne
        @JoinColumn(name = "question_id", insertable = false, updatable = false, nullable = false)
        private Question question;

        public QuestionI18nId(Question question, Language language) {
            super(language);
            this.question = question;
        }

        @Override
        public Question getEntity() {
            return question;
        }
    }

    @Override
    public DTO toDTO() {
        return new DTO(text);
    }
}
