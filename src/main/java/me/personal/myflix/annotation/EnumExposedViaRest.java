package me.personal.myflix.annotation;

import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumExposedViaRest {

	String message() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * Returns the name of the enum used in the API.
     * The enum will be exposed at the following URL: {microservice}/api/enum/{enumApiName}
	 */
	String enumApiName();

	/**
     * Specifies whether the enum should include a multilingual description.
     * If true, the description must be included in the 'messages_it_IT.properties' file using the notation enum.NAME = italian_translation.
     * Example: enum.ORDERED = Ordered
	 */
	boolean multilanguageDescription();
}
