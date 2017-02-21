package com.github.voplex95.plugin.lesscompiler.tasks

import com.github.voplex95.plugin.lesscompiler.utils.Extensions
import com.github.voplex95.plugin.lesscompiler.validation.StringValidator

import static com.github.voplex95.plugin.lesscompiler.utils.FilenameFilters.getFilterForExtension

class SourceInputValidator extends StringValidator {

    @Override
    boolean validate(String subject) {
        !isBlank(subject) && isExistingPath(subject) &&
                ( isFileWithExtension(subject, Extensions.LESS) ||
                        isFolderThatContainsFilesWithExtension(subject, Extensions.LESS) )
    }

    protected static isFileWithExtension(String subject, String extension) {
        def f = new File(subject)
        f.isFile() && f.canRead() && subject.endsWith(extension)
    }

    protected static isFolderThatContainsFilesWithExtension(String subject, String extension) {
        def f = new File(subject)
        return f.isDirectory() && f.canRead() && f.list(getFilterForExtension(extension)).any()
    }

}
