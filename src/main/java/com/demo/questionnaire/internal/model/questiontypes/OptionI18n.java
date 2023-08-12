package com.demo.questionnaire.internal.model.questiontypes;

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

@Table(name = "mc_option_i18n") // option is a reserved word
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor // JPA
public class OptionI18n implements I18nEntity<OptionI18n.DTO> {

    @AllArgsConstructor
    @Getter
    // NOTE: a request only sends a single field set at a time!
    public static class DTO implements I18nEntityDTO<Option, DTO, OptionI18n, OptionI18nId> {
        private static final long serialVersionUID = -6878593190408262249L;
        private final String text;

        @Override
        public I18nIdFactory<Option, OptionI18nId> getIdFactory() {
            return ID_FACTORY;
        }

        @Override
        public I18nFactory<DTO, OptionI18n, OptionI18nId> getI18nFactory() {
            return I18N_FACTORY;
        }

    }

    private final static I18nIdFactory<Option, OptionI18nId> ID_FACTORY = (Language lang,
                                                                           Option entity) -> new OptionI18nId(entity,
            lang);

    private final static I18nFactory<DTO, OptionI18n, OptionI18nId> I18N_FACTORY = (OptionI18nId id,
                                                                                    Optional<OptionI18n> oi18n,
                                                                                    DTO dto) -> {
        // existing or default
        OptionI18n i = oi18n.orElse(new OptionI18n(id, ""));
//        setIfNotNull.accept(dto.getText(), i::setText);
        return i;
    };

    @Embeddable
    @Getter
    @NoArgsConstructor // JPA
    public static class OptionI18nId extends AbstractI18nId<Option, OptionI18nId> {
        private static final long serialVersionUID = 7994350938922161558L;

        @Setter
        @ManyToOne
        @JoinColumn(name = "mc_option_id", insertable = false, updatable = false, nullable = false)
        private Option option;

        public OptionI18nId(Option option, Language language) {
            super(language);
            this.option = option;
        }

        @Override
        public Option getEntity() {
            return option;
        }
    }

    @EmbeddedId
    private OptionI18nId id;

    @Setter
    @NotNull
    @Column(nullable = false)
    private String text;

    @Override
    public DTO toDTO() {
        return new DTO(text);
    }

}
