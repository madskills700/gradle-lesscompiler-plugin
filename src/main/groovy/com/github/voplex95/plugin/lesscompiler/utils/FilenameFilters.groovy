package com.github.voplex95.plugin.lesscompiler.utils

import static org.gradle.internal.impldep.org.apache.commons.lang.StringUtils.isBlank

abstract class FilenameFilters {

    static FilenameFilter withExtension(String extension) {
        new FilenameFilter() {
            @Override
            boolean accept(File dir, String name) {
                dir.canRead() && name.endsWith(extension)
            }
        }
    }

    static FilenameFilter withExtensionExcludePrefix(String extension, String prefix) {
        new FilenameFilter() {
            @Override
            boolean accept(File dir, String name) {
                dir.canRead() && name.endsWith(extension) && !name.startsWith(prefix)
            }
        }
    }

    static FilenameFilter byName(String filename) {
        new FilenameFilter() {
            @Override
            boolean accept(File dir, String name) {
                dir.canRead() && !isBlank(filename) && filename == name
            }
        }
    }

}
