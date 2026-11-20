package com.buschmais.training.jee.shared.jms;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@jakarta.inject.Qualifier
public @interface Pong {
}
