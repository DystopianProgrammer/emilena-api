package com.perks.emilena.validator;

/**
 * Created by Geoff Perks
 * Date: 06/09/2016.
 */
public interface Validator<T> {

    Boolean isValid(T object);
}
