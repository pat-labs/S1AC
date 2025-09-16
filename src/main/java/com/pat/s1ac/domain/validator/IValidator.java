package com.pat.s1ac.domain.validator;

public interface IValidator<T> {
    void isValid(T model) throws IllegalArgumentException;

    void isPartialValid(T model) throws IllegalArgumentException;
}
