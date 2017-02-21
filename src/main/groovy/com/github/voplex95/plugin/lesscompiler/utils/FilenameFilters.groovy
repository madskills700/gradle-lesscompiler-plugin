package com.github.voplex95.plugin.lesscompiler.utils

abstract class FilenameFilters {

    static FilenameFilter getFilterForExtension(String extension) {
        new FilenameFilter() {
            @Override
            boolean accept(File dir, String name) {
                return dir.canRead() && name.endsWith(extension)
            }
        }
    }

}
