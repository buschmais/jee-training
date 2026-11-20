package com.buschmais.training.jee.shared.jms;

import jakarta.inject.Qualifier;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Qualifier
public @interface NonXA {
}
