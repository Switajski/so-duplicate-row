package com.demo.i18n.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * DTO of translation data. Extends Serializable for caching.
 *
 * @param <E>
 * @param <D>
 * @param <T>
 * @param <I>
 
 */
public interface I18nEntityDTO<E extends TranslatedEntity, D extends I18nEntityDTO<E, D, T, I>,
        T extends I18nEntity<D>, I extends I18nId<E, ?>> extends Serializable {
    @JsonIgnore
    I18nIdFactory<E, I> getIdFactory();

    @JsonIgnore
    I18nFactory<D, T, I> getI18nFactory();
}
