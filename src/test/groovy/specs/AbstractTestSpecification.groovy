package specs

import spock.lang.Specification

abstract class AbstractTestSpecification extends Specification {

    protected loadFile(String path) {
        new File(path)
    }

    protected loadRequiredFile(String path) {
        def f = loadFile(path)
        assert f.exists()
        return f
    }

}
