/**
 * Copyright
 */
package foo.bar.tests.drools;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;

import org.drools.io.ResourceFactory;

import org.drools.runtime.StatelessKnowledgeSession;

import foo.bar.BenchmarkTest;

import foo.bar.model.Case;

import foo.bar.util.StopWatch;

public class DroolsBenchmarkTest extends BenchmarkTest {

    private final Logger logger = Logger.getLogger(getClass().getName());

    private KnowledgeBase kbase;

    @Override
    public String getName() {
        return "drools";
    }

    @Override
    public String getDisplayName() {
        return "Drools 5.4.0.Final";
    }

    @Override
    protected void prepare() throws Exception {
        final StopWatch stopWatch = new StopWatch();

        final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("drools/salutation.drl"), ResourceType.DRL);
        if (kbuilder.hasErrors()) {
            for (KnowledgeBuilderError error : kbuilder.getErrors()) {
                logger.log(Level.SEVERE, "{0} @ {1}: {2}",
                    new Object[] {error.getResource(), error.getLines()[0], error.getMessage()});
            }

            throw new Exception("Compilation error. Cannot proceed. See logs for details.");
        }

        kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
    }

    @Override
    protected void process(final Case aCase) {
        final StatelessKnowledgeSession session = kbase.newStatelessKnowledgeSession();
        session.execute(aCase);
    }

}
