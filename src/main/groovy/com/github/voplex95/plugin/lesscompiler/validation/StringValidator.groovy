package com.github.voplex95.plugin.lesscompiler.validation

abstract class StringValidator implements Validator<String> {

    protected static isBlank(String subject) {
        subject == null || subject.empty
    }
    
    protected static isExistingPath(String subject) {
        new File(subject).exists()
    }

}
