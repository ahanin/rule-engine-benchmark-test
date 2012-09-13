/**
 * Copyright
 */
package foo.bar;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import foo.bar.model.Case;
import foo.bar.model.Customer;
import foo.bar.model.Gender;
import foo.bar.model.MaritalStatus;

import foo.bar.tests.drools.DroolsBenchmarkTest;
import foo.bar.tests.openltablets.OpenLTabletsBenchmarkTest;

import foo.bar.util.TimeUtils;

public class RuleEngineBenchmarkTest {

    private final Logger logger = Logger.getLogger(getClass().getName());

    private static final BenchmarkTest[] TESTS = new BenchmarkTest[] {
        new DroolsBenchmarkTest(), new OpenLTabletsBenchmarkTest()
    };

    private static final Random random = new Random();

    private static final Gender[] GENDERS = Gender.values();
    private static final MaritalStatus[] MARITAL_STATUSES = MaritalStatus.values();

    private final int numCases;

    public static void main(final String[] args) {
        new RuleEngineBenchmarkTest(Integer.valueOf(args[0])).test();
    }

    public RuleEngineBenchmarkTest(final int numCases) {
        this.numCases = numCases;
    }

    private void test() {
        final ArrayList<Case> testCases = generateRandomTestCases(numCases);

        logger.log(Level.INFO, "Warming up");

        runTests(generateRandomTestCases(10000)); // warm up

        logger.log(Level.INFO, "Running the tests");

        final List<BenchmarkResult> results = runTests(testCases); // run the tests

        for (BenchmarkResult result : results) {
            final long nanoTimeSpent = result.getNanoTimeSpent();
            final double secondsSpent = TimeUtils.nanoSecondsToSeconds(nanoTimeSpent);
            logger.log(Level.INFO, "{0}: {1,number,#.##} case/s ({2,number,#.##} cases, {3}s)",
                new Object[] {
                    result.getTestName(), (double) testCases.size() / secondsSpent, testCases.size(), secondsSpent
                });

        }
    }

    private List<BenchmarkResult> runTests(final ArrayList<Case> testCases) {
        final List<BenchmarkResult> results = new LinkedList<BenchmarkResult>();

        for (BenchmarkTest test : TESTS) {
            results.add(test.process(testCases));
        }

        return results;
    }

    private ArrayList<Case> generateRandomTestCases(final int numCases) {
        final ArrayList<Case> testCases = new ArrayList<Case>(numCases);
        for (int i = 0; i < numCases; i++) {
            final Case aCase = new Case();

            final MaritalStatus maritalStatus = MARITAL_STATUSES[Math.abs(random.nextInt()) % MARITAL_STATUSES.length];
            final Gender gender = GENDERS[Math.abs(random.nextInt()) % GENDERS.length];

            final Customer customer = new Customer();
            customer.setGender(gender);
            customer.setMaritalStatus(maritalStatus);
            customer.setName("John Doe");

            aCase.setCustomer(customer);
            aCase.setHourOfDay(Math.abs(random.nextInt()) % 24);

            testCases.add(aCase);
        }

        return testCases;
    }

}
