# gradle-lesscompiler-plugin
Lightweight LESS to CSS Compiler plugin for Gradle. Uses <a href="https://github.com/i-net-software/jlessc">jlessc</a> compiler.

https://plugins.gradle.org/plugin/com.github.voplex95.lesscompiler

## Integration
Build script snippet for use in all Gradle versions:
```gradle
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.github.voplex95:gradle-lesscompiler-plugin:1.0.1"
  }
}

apply plugin: "com.github.voplex95.lesscompiler"
```

Build script snippet for new, incubating, plugin mechanism introduced in Gradle 2.1:
```gradle
plugins {
  id "com.github.voplex95.lesscompiler" version "1.0.1"
}
```

## Usage
Configuration example:
```gradle
lessCompile {
    source = file('src/main/resources/less')
    target = file('src/main/resources/public/css')
    compress = true
}
```
Available properties:
  - **source** - (file) LESS file or directory with LESS files
  - **target** - (file) directory for compiled CSS files
  - **compress** - (boolean, default ```false```) whether or not CSS should be minified
  - **excludePrefix** - (string, default ```_```) can be used to exclude "partial" files (```@include```-s)
