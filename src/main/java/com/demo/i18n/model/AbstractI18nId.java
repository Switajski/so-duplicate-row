package com.demo.i18n.model;

import java.io.Serializable;

import jakarta.annotation.Nonnull;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Common implementation for {@link I18nId}s.
 * <p>
 * Subclasses should define a serialVersionUID.
 *
 * @param <T>  The type of the {@link TranslatedEntity}
 * @param <ID> the {@link I18nId}
 
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor // JPA
@MappedSuperclass
abstract public class AbstractI18nId<
        T extends TranslatedEntity,
        ID extends I18nId<T, ID>>
        implements I18nId<T, ID>,
        Serializable /* @Embeddable classes should be serializable, especially when
                         used as @EmbeddableId because the id might be used as a
                         key in Hibernate's 2nd-level cache entries. */ {

    @Setter
    @ManyToOne
    @JoinColumn(name = "language_id", insertable = false, updatable = false, nullable = false)
    private Language language;
}
