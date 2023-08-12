package com.demo.questionnaire.internal.model.questiontypes;

import java.util.ArrayList;
import java.util.List;

import com.demo.i18n.model.I18n;
import com.demo.i18n.model.TranslatedEntity;
import com.demo.questionnaire.internal.model.QuestionConfig;
import com.demo.questionnaire.internal.model.questiontypes.OptionI18n.DTO;
import com.demo.questionnaire.internal.model.questiontypes.OptionI18n.OptionI18nId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mc_option") // option is a reserved word
@NoArgsConstructor // JPA
@Getter
public class Option implements TranslatedEntity, I18n<Option, DTO, OptionI18n, OptionI18nId> {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "config_id", nullable = false)
    private QuestionConfig<?> config;

    @OneToMany(mappedBy = "id.option", cascade = CascadeType.ALL, orphanRemoval = true)
    final private List<OptionI18n> i18ns = new ArrayList<>();

    public Option(QuestionConfig<?> config) {
        super();
        this.config = config;
    }
}
