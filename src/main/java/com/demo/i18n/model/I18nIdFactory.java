package com.demo.i18n.model;

/**
 * Creates an {@link I18nId} based on a given {@link Language} and an {@link TranslatedEntity}.
 *
 * @param <E>
 * @param <I>
 
 */
public interface I18nIdFactory<E extends TranslatedEntity, I extends I18nId<E, ?>> {
    I create(Language lang, E entity);
}
