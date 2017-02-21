package com.github.voplex95.plugin.lesscompiler.tasks

import com.github.voplex95.plugin.lesscompiler.InputValidationException
import com.github.voplex95.plugin.lesscompiler.utils.Extensions
import com.inet.lib.less.Less
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

import static com.github.voplex95.plugin.lesscompiler.utils.FilenameFilters.getFilterForExtension

class LessCompileTask extends DefaultTask {

    @Input
    String source

    @Input
    String target

    @Optional @Input
    Boolean compress = false

    @TaskAction
    void run() throws InputValidationException {

        if(!new SourceInputValidator().validate(source)) {
            throw new InputValidationException("Source path $source is invalid. Nothing to compile")
        }

        if(!new TargetInputValidator().validate(target)) {
            throw new InputValidationException("Target path $target is invalid")
        }

        File[] lessFiles = listLessFilesForCompilation(source)

        println lessFiles.length

        for(File lessFile in lessFiles) {
            def compiledContent = Less.compile(lessFile, compress)
            def destinationPath = new File(target).isFile() ? target :
                    target + File.separator + lessToCssFileName(lessFile.name)
            try{
                PrintWriter writer = new PrintWriter(destinationPath, "UTF-8")
                writer.print(compiledContent)
                writer.close()
            } catch (IOException e) {
                e.printStackTrace()
            }
        }

    }

    private listLessFilesForCompilation(String source) {
        def s = new File(source)
        s = s.isDirectory() ? s : s.getParentFile()
        return s.isFile() ? s : s.listFiles(getFilterForExtension(Extensions.LESS))
    }

    private lessToCssFileName(String lessName) {
        lessName.replace(Extensions.LESS, compress ? Extensions.MINIFIED_CSS : Extensions.CSS)
    }

}
