package com.demo.questionnaire.internal.model;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor // JPA
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class QuestionConfig<T extends QuestionConfig<T>> {
    @Setter
    @Id
    // Not generated but shared with Answer
    private Long id;

    @Setter
    @OneToOne(fetch = FetchType.LAZY) // managed by Question.setConfig()
    @MapsId
    @JoinColumn(name = "id") // otherwise it would be question_id
    // synchronized by question.setConfig()
    // see https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/
    private Question question;
}
