package com.demo.i18n.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findOneByLocale(String locale);
}
