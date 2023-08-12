package com.demo.i18n.model;

import java.util.Optional;

/**
 * Can update or create an {@link I18nEntity} translation based on a given {@link I18nEntityDTO}.
 *
 * @param <D>
 * @param <T>
 * @param <I>
 
 */
public interface I18nFactory<D extends I18nEntityDTO<?, D, T, I>, T extends I18nEntity<D>, I extends I18nId<?, ?>> {
    T updateOrCreate(I id, Optional<T> oi18n, D dto);
}
