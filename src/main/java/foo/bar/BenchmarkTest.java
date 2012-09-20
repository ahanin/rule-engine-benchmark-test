/**
 * Copyright
 */
package foo.bar;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import foo.bar.model.Case;

import foo.bar.util.StopWatch;

public abstract class BenchmarkTest {

    private Logger logger = Logger.getLogger(getName());

    public abstract String getName();

    public abstract String getDisplayName();

    protected abstract void prepare() throws Exception;

    protected abstract void process(final Case aCase);

    public final BenchmarkResult process(final Collection<Case> cases) {
        final BenchmarkResult result = new BenchmarkResult();

        final StopWatch stopWatch = new StopWatch();

        for (Case aCase : cases) {
            logger.log(Level.FINEST, "customer={0}, hourOfDay={1}",
                new Object[] {aCase.getCustomer(), aCase.getHourOfDay()});
            process(aCase);
        }

        result.setNanoTimeSpent(stopWatch.getNanoSeconds());
        result.setNumProcessedItems(cases.size());
        result.setTestName(getName());

        return result;
    }

}
