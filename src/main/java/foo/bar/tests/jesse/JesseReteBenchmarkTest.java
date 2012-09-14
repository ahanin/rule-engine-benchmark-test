package foo.bar.tests.jesse;

import foo.bar.BenchmarkTest;
import foo.bar.model.Case;
import jess.JessException;
import jess.Rete;

import java.io.FileNotFoundException;
import java.net.URL;

public class JesseReteBenchmarkTest extends BenchmarkTest {

    private static final String GREETING_RULE = "jesse/salutation.clp";
    private Rete rete;

    @Override
    public String getName() {
        return "Jesse 7.1 Rete";
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
        rete.reset();
    }

    @Override
    protected void process(final Case aCase) {
        try {
            rete.reset();
            rete.add(aCase);
            rete.run();
        } catch (JessException e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }
}
