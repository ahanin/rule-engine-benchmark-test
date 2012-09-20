/**
 * Copyright
 */
package foo.bar;

import foo.bar.model.Case;
import foo.bar.model.Customer;
import foo.bar.model.Gender;
import foo.bar.model.MaritalStatus;

import foo.bar.tests.drools.DroolsBenchmarkTest;
import foo.bar.tests.jesse.JessReteBenchmarkTest;
import foo.bar.tests.openltablets.OpenLTabletsBenchmarkTest;

import foo.bar.util.HeapDumper;
import foo.bar.util.StopWatch;
import foo.bar.util.TimeUtils;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RuleEngineBenchmarkTest {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final BenchmarkTest[] TESTS = new BenchmarkTest[] {
            new DroolsBenchmarkTest(), new OpenLTabletsBenchmarkTest(),
            new JessReteBenchmarkTest()
        };
    private static final Random random = new Random();
    private static final Gender[] GENDERS = Gender.values();
    private static final MaritalStatus[] MARITAL_STATUSES = MaritalStatus.values();
    private final Map<String,BenchmarkTest> testMap = new HashMap<String,BenchmarkTest>();
    private final int numCases;
    private final int numWarmUpCases;
    private String[] testNames = new String[0];
    private String heapDumpFile;
    private List<BenchmarkTest> tests;

    public static void main(final String[] args) throws Exception {
        final String heapDumpFileParam = ((args.length > 3) &&
            (args[3] != null)) ? String.valueOf(args[3]) : null;

        final Integer numWarmUpCases = ((args.length > 2) && (args[2] != null))
            ? Integer.valueOf(args[2]) : 0;

        final Integer numCases = (args.length > 1) ? Integer.valueOf(args[1]) : 0;

        final String[] testsParam = (args.length > 0)
            ? args[0].split("\\s*,\\s*") : new String[0];

        if (args.length < 1) {
            System.out.println(
                "Usage: <tests> [ <test-number> [ <warm-up-test-number> [ <heap-dump-file>] ] ]");

            final StringBuilder sb = new StringBuilder();

            for (int i = 0, testsLength = TESTS.length; i < testsLength; i++) {
                final BenchmarkTest test = TESTS[i];
                sb.append(test.getName());

                if (i < (TESTS.length - 1)) {
                    sb.append(", ");
                }
            }

            System.out.println("Available tests: " + sb.toString());
            System.exit(-1);
        }

        final RuleEngineBenchmarkTest benchmarkTest = new RuleEngineBenchmarkTestBuilder().setNumCases(numCases)
                                                                                          .setNumWarmUpCases(numWarmUpCases)
                                                                                          .setTests(testsParam)
                                                                                          .setHeapDumpFile(heapDumpFileParam)
                                                                                          .createRuleEngineBenchmarkTest();
        benchmarkTest.test();
    }

    public RuleEngineBenchmarkTest(final int numCases,
        final int numWarmUpCases, final String[] tests,
        final String heapDumpFile) {
        if (numCases < 1) {
            throw new IllegalArgumentException(
                "Number of test cases to run must be positive");
        }

        if (numWarmUpCases < 0) {
            throw new IllegalArgumentException(
                "Number of warm-up test cases cannot be negative");
        }

        if (tests.length < 1) {
            throw new IllegalArgumentException(
                "At least one test must be chosen to run");
        }

        this.numCases = numCases;
        this.numWarmUpCases = numWarmUpCases;
        this.testNames = tests;
        this.heapDumpFile = heapDumpFile;

        initTests();
    }

    private void initTests() {
        for (BenchmarkTest test : TESTS) {
            testMap.put(test.getName(), test);
        }

        tests = new LinkedList<BenchmarkTest>();

        for (String testName : testNames) {
            tests.add(this.testMap.get(testName));
        }
    }

    private void test() throws Exception {
        final ArrayList<Case> testCases = generateRandomTestCases(numCases);

        logger.log(Level.INFO, "Preparing tests");

        for (BenchmarkTest test : tests) {
            final StopWatch stopWatch = new StopWatch();
            test.prepare();
            logger.log(Level.INFO, "{0} prepared in {1}s",
                new Object[] { test.getDisplayName(), stopWatch.formatSeconds() });
        }

        if (numWarmUpCases > 0) {
            logger.info("Warming up");
            runTests(generateRandomTestCases(numWarmUpCases)); // warm up
        }

        logger.info("Running the tests");

        final List<BenchmarkResult> results = runTests(testCases); // run the tests

        for (BenchmarkResult result : results) {
            final long nanoTimeSpent = result.getNanoTimeSpent();
            final double secondsSpent = TimeUtils.nanoSecondsToSeconds(nanoTimeSpent);
            logger.log(Level.INFO,
                "{0}: {1,number,#.##} case/s ({2,number,#.##} cases, {3}s)",
                new Object[] {
                    result.getTestName(),
                    (double) testCases.size() / secondsSpent, testCases.size(),
                    secondsSpent
                });
        }

        if (heapDumpFile != null) {
            final File aFile = new File(heapDumpFile);

            if (aFile.exists()) {
                if (!aFile.delete()) {
                    logger.warning("Unable to delete file " + heapDumpFile);
                }
            }

            HeapDumper.dumpHeap(heapDumpFile, false);
        }
    }

    private List<BenchmarkResult> runTests(final ArrayList<Case> testCases) {
        final List<BenchmarkResult> results = new LinkedList<BenchmarkResult>();

        for (BenchmarkTest test : tests) {
            logger.log(Level.INFO, "Starting {0} test", test.getName());
            results.add(test.process(testCases));
            logger.log(Level.INFO, "{0} test finished", test.getName());
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
            customer.setName("Smith");

            aCase.setCustomer(customer);
            aCase.setHourOfDay(Math.abs(random.nextInt()) % 24);

            testCases.add(aCase);
        }

        return testCases;
    }
}
