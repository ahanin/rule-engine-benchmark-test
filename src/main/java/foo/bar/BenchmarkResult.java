package foo.bar;

public class BenchmarkResult {

    private String testName;
    private long nanoTimeSpent;
    private int numProcessedItems;

    public String getTestName() {
        return testName;
    }

    public void setTestName(final String testName) {
        this.testName = testName;
    }

    public long getNanoTimeSpent() {
        return nanoTimeSpent;
    }

    public void setNanoTimeSpent(final long nanoTimeSpent) {
        this.nanoTimeSpent = nanoTimeSpent;
    }

    public int getNumProcessedItems() {
        return numProcessedItems;
    }

    public void setNumProcessedItems(final int numProcessedItems) {
        this.numProcessedItems = numProcessedItems;
    }

}
