package com.github.voplex95.plugin.lesscompiler.tasks.lesscompile

import com.github.voplex95.plugin.lesscompiler.utils.Extensions
import com.github.voplex95.plugin.lesscompiler.validation.Validator

import static com.github.voplex95.plugin.lesscompiler.utils.FilenameFilters.withExtension

class SourceInputValidator implements Validator<File> {

    @Override
    boolean validate(File subject) {
        subject.exists() &&
                ( isFileWithExtension(subject, Extensions.LESS) ||
                        isFolderThatContainsFilesWithExtension(subject, Extensions.LESS) )
    }

    protected static isFileWithExtension(File f, String extension) {
        f.isFile() && f.canRead() && f.name.endsWith(extension)
    }

    protected static isFolderThatContainsFilesWithExtension(File f, String extension) {
        return f.isDirectory() && f.canRead() && f.list(withExtension(extension)).any()
    }

}
