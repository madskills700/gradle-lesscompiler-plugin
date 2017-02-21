package com.github.voplex95.plugin.lesscompiler.validation

interface Validator<T> {
    boolean validate(T subject)
}