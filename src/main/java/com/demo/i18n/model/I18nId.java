package com.demo.i18n.model;

import java.io.Serializable;

/**
 * Id of an {@link I18nEntity} translation. They consist of the id of the related {@link TranslatedEntity} and the id of
 * the related {@link Language}. Implementations should extend {@link AbstractI18nId} for proper equals and hashCode
 * implementations.
 *
 * @param <E>
 
 * @see AbstractI18nId
 */
public interface I18nId<E extends TranslatedEntity, ID extends I18nId<E, ID>> extends Serializable {
    E getEntity();

    Language getLanguage();

}
