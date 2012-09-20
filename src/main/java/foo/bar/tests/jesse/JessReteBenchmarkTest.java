package foo.bar.tests.jesse;

import foo.bar.BenchmarkTest;
import foo.bar.model.Case;
import jess.JessException;
import jess.Rete;
import jess.WorkingMemoryMarker;

import java.io.FileNotFoundException;
import java.net.URL;

public class JessReteBenchmarkTest extends BenchmarkTest {

    private static final String GREETING_RULE = "jesse/salutation.clp";
    private Rete rete;
    private WorkingMemoryMarker beginMark;

    @Override
    public String getName() {
        return "jess";
    }

    @Override
    public String getDisplayName() {
        return "Jess 7.1 Rete";
    }

    @Override
    protected void prepare() throws Exception {
        rete = new Rete();
        final URL url = getClass().getClassLoader().getResource(GREETING_RULE);
        if (url == null) {
            throw new FileNotFoundException(GREETING_RULE + " not found");
        }
        final String file = url.getFile();
        rete.batch(file);
        beginMark = rete.mark();
    }

    @Override
    protected void process(final Case aCase) {
        try {
            rete.resetToMark(beginMark);
            rete.add(aCase);
            rete.run();
        } catch (JessException e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }
}
