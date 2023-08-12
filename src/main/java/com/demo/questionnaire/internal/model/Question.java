package com.demo.questionnaire.internal.model;

import java.util.ArrayList;
import java.util.List;

import com.demo.i18n.model.TranslatedEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
public class Question implements TranslatedEntity {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Question(QuestionConfig<?> config) {
        // custom setter ensures bidirectional assoc
        setConfig(config);
    }

    // Question and QuestionConfig use the same id.
    // In general Question is the parent entity that is operated on, hence synchronization is done by question
    // .setConfig().
    // Technically, however, QuestionConfig owns the relationship, because Question does not know any unique index in
    // QuestionConfig.
    // Instead, QuestionConfig references/uses the id of Question and hence becomes the owning entity.
    // See https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/
    // @Setter implemented
    @OneToOne(
            mappedBy = "question",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "config_id", nullable = false)
    private QuestionConfig<?> config;

    @OneToMany(mappedBy = "id.question", cascade = CascadeType.ALL, orphanRemoval = true)
    final private List<QuestionI18n> i18ns = new ArrayList<>();

    public void setConfig(QuestionConfig<?> config) {
        // custom setter ensures bidirectional assoc
        // -> see class doc.
        if (config == null) {
            if (this.config != null) {
                this.config.setQuestion(null);
            }
        } else {
            config.setQuestion(this);
        }
        this.config = config;
    }
}
