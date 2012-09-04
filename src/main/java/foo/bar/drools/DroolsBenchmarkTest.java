/**
 * Copyright
 */
package foo.bar.drools;

import foo.bar.BenchmarkTest;
import foo.bar.model.Case;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;

import java.util.Collection;

public class DroolsBenchmarkTest implements BenchmarkTest {

    private final KnowledgeBase kbase;

    public DroolsBenchmarkTest() {
        final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("customer.drl"), ResourceType.DRL);
        if (kbuilder.hasErrors()) {
            for (KnowledgeBuilderError error : kbuilder.getErrors()) {
                System.out.println(error.getMessage());
            }
        }

        kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
    }

    @Override
    public String getName() {
        return "Drools 5.4.0.Final";
    }

    @Override
    public void process(Collection<Case> cases) {
        for (Case aCase : cases) {
            final StatelessKnowledgeSession session = kbase.newStatelessKnowledgeSession();
            session.execute(aCase.getCustomer());
        }
    }

}
