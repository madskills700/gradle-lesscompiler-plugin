package com.github.voplex95.plugin.lesscompiler.tasks

import com.github.voplex95.plugin.lesscompiler.InputValidationException
import com.inet.lib.less.Less
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

/**
 * Created by Illia Chtchoma on 19/02/2017.
 */
class LessCompileTask extends DefaultTask {

    private static final String LESS_EXTENSION = ".less"
    private static final String CSS_EXTENSION = ".css"

    @Input
    String srcDir

    @Input
    String targetDir

    @Input
    @Optional
    Boolean compress = false

    @TaskAction
    void exec() {

        def src = new File(srcDir)
        def target = new File(targetDir)

        if(!isValidSource(src)) {
            throw new InputValidationException("Source path $srcDir is invalid. Can not find LESS file(s)")
        }

        if(!isValidTarget(src)) {
            throw new InputValidationException("Target path $target is invalid")
        }

        println "Execution parameters: {source=$srcDir, destination=$targetDir, compress=$compress}"

        for(File lessFile in listLessFiles(src)) {
            def compiledContent = Less.compile(lessFile, compress)
            def cssFileName = lessFile.name.replace(LESS_EXTENSION, CSS_EXTENSION)
            try{
                PrintWriter writer = new PrintWriter(targetDir + File.separator + cssFileName, "UTF-8")
                writer.print(compiledContent)
                writer.close()
            } catch (IOException e) {
                e.printStackTrace()
            }
        }

    }

    private static isValidSource(File src) {
        src.isDirectory() && src.canRead() && listLessFiles(src).any()
    }

    private static isValidTarget(File target) {
        target.isDirectory() && target.canWrite()
    }

    private static listLessFiles(src) {
        src.listFiles(new FileFilter() {
            @Override
            boolean accept(File pathname) {
                return pathname != null && pathname.getName().endsWith(LESS_EXTENSION)
            }
        })
    }

}
