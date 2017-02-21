package com.github.voplex95.plugin.lesscompiler.tasks.lesscompile

import com.github.voplex95.plugin.lesscompiler.validation.StringValidator

class TargetInputValidator extends StringValidator {

    @Override
    boolean validate(String subject) {
        !isBlank(subject) && !subject.endsWith(File.separator)
    }

}
