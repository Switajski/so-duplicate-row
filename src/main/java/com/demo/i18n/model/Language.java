package com.demo.i18n.model;

import java.util.ArrayList;
import java.util.List;

import com.demo.questionnaire.internal.model.QuestionI18n;
import com.demo.questionnaire.internal.model.questiontypes.OptionI18n;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Language {
    public Language(@NotNull String locale) {
        this.locale = locale;
    }

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    @NotNull
    @Column(nullable = false, unique = true)
    private String locale;

    @OneToMany(mappedBy = "id.language", cascade = CascadeType.ALL, orphanRemoval = true)
    final private List<OptionI18n> optionI18ns = new ArrayList<>();

    @OneToMany(mappedBy = "id.language", cascade = CascadeType.ALL, orphanRemoval = true)
    final private List<QuestionI18n> questionI18ns = new ArrayList<>();
}
