package specs

import org.apache.commons.io.FileUtils
import spock.lang.Specification

abstract class AbstractTestSpecification extends Specification {

    static final String TEST_RESOURCES_PATH = 'src/test/resources'

    protected void cleanDirectory(String path) {
        FileUtils.cleanDirectory(new File(TEST_RESOURCES_PATH + path))
    }

    protected File loadRequiredFile(String path) {
        def f = new File(TEST_RESOURCES_PATH + path)
        assert f.exists()
        return f
    }

}
