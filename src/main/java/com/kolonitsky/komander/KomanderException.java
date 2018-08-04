package com.kolonitsky.komander;

/**
 * Created by akalanitski on 02.07.2018.
 */
public class KomanderException extends Exception {

	public KomanderException(String message) {
		super(message);
	}

	public static final KomanderException UndefinedFlag(String flag) {
		return new KomanderException(String.format("Flag `%s` is not defined", flag));
	}
	public static final KomanderException DublicateFlagDefinition(String flag) {
		return new KomanderException(String.format("Flag `%s` is already defined", flag));
	}
	public static final KomanderException DuplicateParameterDefinition(String name) {
		return new KomanderException(String.format("Parameter `%s` is already defined", name));
	}
}
