package com.github.voplex95.plugin.lesscompiler.utils

abstract class FilenameFilters {

    static FilenameFilter withExtension(String extension) {
        new FilenameFilter() {
            @Override
            boolean accept(File dir, String name) {
                return dir.canRead() && name.endsWith(extension)
            }
        }
    }

    static FilenameFilter withExtensionExcludePrefix(String extension, String prefix) {
        new FilenameFilter() {
            @Override
            boolean accept(File dir, String name) {
                return dir.canRead() && name.endsWith(extension) && !name.startsWith(prefix)
            }
        }
    }

}
