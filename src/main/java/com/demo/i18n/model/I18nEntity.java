package com.demo.i18n.model;

/**
 * The translation data of an entity.
 *
 * @param <D>
 
 */
public interface I18nEntity<D extends I18nEntityDTO<?, ?, ?, ?>> {
    D toDTO();
}
