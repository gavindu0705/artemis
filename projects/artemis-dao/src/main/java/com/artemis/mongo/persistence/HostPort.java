package com.artemis.mongo.persistence;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 192.168.0.136:27017
 * 
 * @author xiaoyu
 * 
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface HostPort {
	String host();

	int port();

}
