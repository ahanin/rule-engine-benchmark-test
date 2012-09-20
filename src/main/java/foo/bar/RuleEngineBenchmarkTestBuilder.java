package foo.bar;

public class RuleEngineBenchmarkTestBuilder {
    private int numCases;
    private Integer numWarmUpCases;
    private String[] tests;
    private String heapDumpFile;

    public RuleEngineBenchmarkTestBuilder setNumCases(final int numCases) {
        this.numCases = numCases;
        return this;
    }

    public RuleEngineBenchmarkTestBuilder setNumWarmUpCases(final Integer numWarmUpCases) {
        this.numWarmUpCases = numWarmUpCases;
        return this;
    }

    public RuleEngineBenchmarkTestBuilder setTests(final String[] tests) {
        this.tests = tests;
        return this;
    }

    public RuleEngineBenchmarkTestBuilder setHeapDumpFile(final String heapDumpFile) {
        this.heapDumpFile = heapDumpFile;
        return this;
    }

    public RuleEngineBenchmarkTest createRuleEngineBenchmarkTest() {
        return new RuleEngineBenchmarkTest(numCases, numWarmUpCases, tests, heapDumpFile);
    }
}