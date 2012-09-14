package foo.bar.tests.openltablets;

import org.openl.rules.runtime.RuleEngineFactory;

import org.openl.runtime.EngineFactory;

import foo.bar.BenchmarkTest;

import foo.bar.model.Case;

public class OpenLTabletsBenchmarkTest extends BenchmarkTest {

    private IExample instance;

    @Override
    public String getName() {
        return "OpenL Tablets 5.9.3.1";
    }

    @Override
    protected void prepare() throws Exception {
        final EngineFactory<IExample> engineFactory = new RuleEngineFactory<IExample>(getClass().getClassLoader()
                    .getResource("openltablets/HelloExcelCustomer.xls"), IExample.class);
        instance = engineFactory.makeInstance();
    }

    @Override
    protected void process(final Case aCase) {
        instance.helloCustomer(aCase, new Response());
    }

}
