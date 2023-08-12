package com.demo.i18n.model;

/**
 * Marks an Entity to be translated.
 *
 * @param <E> the entity being translated
 * @param <D> the respective DTO to store and retrieve translations
 * @param <T> the translation entity storing translations of the original entity.
 * @param <I> the id type of the translation entity.
 
 */
public interface I18n<E extends TranslatedEntity, D extends I18nEntityDTO<E, D, T, I>, T extends I18nEntity<D>,
        I extends I18nId<E, ?>> {

}
