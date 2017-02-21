package com.github.voplex95.plugin.lesscompiler

import com.github.voplex95.plugin.lesscompiler.tasks.lesscompile.LessCompileTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class LessCompilerPlugin implements Plugin<Project> {

    def PLUGIN_GROUP = 'less'

    @Override
    void apply(Project target) {
        target.task('lessCompile', type: LessCompileTask, group: PLUGIN_GROUP) {}
    }

}