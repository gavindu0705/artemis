package com.artemis.mongo.persistence;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(TYPE)
@Retention(RUNTIME)
public @interface DBConfiguration {
	String db();

	String collection();

	String host();

	int port() default 27017;

}
