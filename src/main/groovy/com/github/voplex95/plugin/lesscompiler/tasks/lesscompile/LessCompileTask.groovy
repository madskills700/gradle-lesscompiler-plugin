package com.github.voplex95.plugin.lesscompiler.tasks.lesscompile

import com.github.voplex95.plugin.lesscompiler.validation.InputValidationException
import com.github.voplex95.plugin.lesscompiler.utils.Extensions
import com.inet.lib.less.Less
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

import static com.github.voplex95.plugin.lesscompiler.utils.FilenameFilters.withExtension
import static com.github.voplex95.plugin.lesscompiler.utils.FilenameFilters.withExtensionExcludePrefix

class LessCompileTask extends DefaultTask {

    @Input
    File source

    @Input
    File target

    @Input
    @Optional
    String excludePrefix = '_'

    @Input
    @Optional
    Boolean compress = false

    @TaskAction
    void run() throws InputValidationException, IOException {

        if(!new SourceInputValidator().validate(source)) {
            throw new InputValidationException("Source path $source.absolutePath is invalid. Nothing to compile")
        }

        if(!new TargetInputValidator().validate(target)) {
            throw new InputValidationException("Target path $target.absolutePath is invalid")
        }

        for(lessFile in listMatches()) {
            def compiledContent = Less.compile((File)lessFile, compress)
            def destinationPath = target.absolutePath
            if(target.isDirectory()) {
               destinationPath = new File(destinationPath, composeCssFileName(((File)lessFile).name))
            }

            PrintWriter writer = new PrintWriter(destinationPath, "UTF-8")
            writer.print(compiledContent)
            writer.close()

        }

    }

    private listMatches() {
        if(source.isFile()) {
            return [source]
        }
        return source.name.startsWith(excludePrefix) ?
                source.listFiles(withExtension(Extensions.LESS)) :
                source.listFiles(withExtensionExcludePrefix(Extensions.LESS, excludePrefix))
    }

    private composeCssFileName(String lessFileName) {
        lessFileName.replace(Extensions.LESS, compress ? Extensions.MINIFIED_CSS : Extensions.CSS)
    }

}
