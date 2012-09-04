/**
 * Copyright
 */
package foo.bar;

import foo.bar.model.Case;

import java.util.Collection;

public interface BenchmarkTest {

    String getName();

    void process(Collection<Case> customers);

}
