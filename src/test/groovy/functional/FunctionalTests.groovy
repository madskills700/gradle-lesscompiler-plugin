package functional

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import specs.AbstractTestSpecification

class FunctionalTests extends AbstractTestSpecification {

    static final String CASE_DIR = '/case_1'
    static final String OUTPUT_DIR = CASE_DIR + '/output'

    @Rule
    TemporaryFolder temporaryFolder = new TemporaryFolder()

    File buildFile

    def setup() {
        buildFile = temporaryFolder.newFile('build.gradle')
        cleanDirectory(OUTPUT_DIR)
    }

    def "compiles less to css" () {
        given:
        this.buildFile << loadRequiredFile(CASE_DIR + '/gradle_build_config').text

        when:
        def result = GradleRunner.create()
                .withProjectDir(temporaryFolder.root)
                .withArguments('lessCompile')
                .build()

        then:
        assert result.task(":lessCompile").outcome == TaskOutcome.SUCCESS
        def compiledFileContent = loadRequiredFile(OUTPUT_DIR + '/style.min.css').text
        assert compiledFileContent != null && compiledFileContent.contains('background-color:#222')
    }

}
