package com.github.voplex95.plugin.lesscompiler.tasks.lesscompile

import com.github.voplex95.plugin.lesscompiler.validation.Validator

class TargetInputValidator implements Validator<File> {

    @Override
    boolean validate(File subject) {
        !subject.name.endsWith(File.separator)
    }

}
