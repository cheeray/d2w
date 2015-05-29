package com.d2w.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * Annotate a class or field to be exposed with a path. If Enum need to be
 * exposed, annotate on the package is required.
 * 
 * @author Chengwei.Yan
 * 
 */
@Inherited
@InterceptorBinding
@Retention(RUNTIME)
@Target({ FIELD, TYPE, PACKAGE })
public @interface Tow {
	/**
	 * Path of data.
	 * 
	 * @return a string to represent the path.
	 */
	String path() default "";

	/**
	 * Media type of data to be represented.
	 * 
	 * @return a string to represent the path.
	 */
	String type() default "*/*";
}