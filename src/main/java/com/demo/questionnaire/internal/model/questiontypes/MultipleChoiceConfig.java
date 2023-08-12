package com.demo.questionnaire.internal.model.questiontypes;

import java.util.ArrayList;
import java.util.List;

import com.demo.questionnaire.internal.model.QuestionConfig;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor // JPA
@Getter
@DiscriminatorValue(MultipleChoiceConfig.TYPE)
public class MultipleChoiceConfig extends QuestionConfig<MultipleChoiceConfig> {
    public static final String TYPE = "MULTIPLE_CHOICE";

    @OneToMany(mappedBy = "config", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    final private List<Option> options = new ArrayList<>();
}
