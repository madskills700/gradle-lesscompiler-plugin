package functional

import org.apache.commons.io.FileUtils
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import specs.AbstractTestSpecification

class FunctionalTests extends AbstractTestSpecification {

    @Rule
    TemporaryFolder temporaryFolder = new TemporaryFolder()

    File buildFile
    File inputFolder
    File outputFolder

    def setup() {
        buildFile = temporaryFolder.newFile('build.gradle')
        inputFolder = temporaryFolder.newFolder('input')
        outputFolder = temporaryFolder.newFolder('output')
    }

    def "compile and minify less file located in specified folder" () {
        given:
        this.buildFile << loadRequiredFile(testResource('/case_1/gradle_build_config')).text
        FileUtils.copyDirectory(loadRequiredFile(testResource('/case_1/input')), inputFolder)

        when:
        def result = GradleRunner.create()
                .withProjectDir(temporaryFolder.root)
                .withArguments('lessCompile')
                .build()

        then:

        // make sure task is succeed
        assert result.task(":lessCompile").outcome == TaskOutcome.SUCCESS

        // make sure less files from nested folders not compiled as standalone css files
        assert !loadFile(output('/variables.min.css')).exists()

        // make sure variables from source less file and partial less files included to css file
        def compiledFileContent = loadRequiredFile(output('/style.min.css')).text
        assert compiledFileContent != null && compiledFileContent.contains('background-color:#222') &&
                compiledFileContent.contains('color:#333')
    }

    def "compile less file located in specified folder and ignore prefixed files" () {
        given:
        this.buildFile << loadRequiredFile(testResource('/case_2/gradle_build_config')).text
        FileUtils.copyDirectory(loadRequiredFile(testResource('/case_2/input')), inputFolder)

        when:
        def result = GradleRunner.create()
                .withProjectDir(temporaryFolder.root)
                .withArguments('lessCompile')
                .build()

        then:

        // make sure task is succeed
        assert result.task(":lessCompile").outcome == TaskOutcome.SUCCESS

        // make sure less files with name starting with excludePrefix not compiled as standalone css files
        assert !loadFile(output('/_variables.css')).exists()

        // make sure css is not compressed
        def compiledFileContent = loadRequiredFile(output('/style.css')).text
        assert compiledFileContent != null && compiledFileContent.contains('background-color: #222222')
    }

    def "compile specified less file (only) even if it starts with excludePrefix" () {
        given:
        this.buildFile << loadRequiredFile(testResource('/case_3/gradle_build_config')).text
        FileUtils.copyDirectory(loadRequiredFile(testResource('/case_3/input')), inputFolder)

        when:
        def result = GradleRunner.create()
                .withProjectDir(temporaryFolder.root)
                .withArguments('lessCompile')
                .withDebug(true)
                .build()

        then:

        // make sure task is succeed
        assert result.task(":lessCompile").outcome == TaskOutcome.SUCCESS

        // make sure required file was compiled to css file
        loadRequiredFile(output('/_variables.css'))

        // make sure other non-prefixed files were not compiled
        assert !loadFile(output('/style.css')).exists()
    }

    private output() {
        outputFolder.absolutePath
    }

    private testResource(String path) {
        'src/test/resources' + path
    }

    private output(String path) {
        output() + path
    }

}
