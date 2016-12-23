package com.perks.emilena.validation;

/**
 * Emilena project copyright - 2016
 * Created by Geoff Perks.
 * Date: 20/12/2016
 */
public interface ValidateCompare<C, T> {

    Boolean isValid(C first, T second);
}
