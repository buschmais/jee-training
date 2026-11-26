package com.buschmais.training.jee.adapters.secondary.jpa;

import java.lang.annotation.Retention;

import jakarta.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
public @interface Books {
}
