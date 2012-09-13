package foo.bar.util;

import java.text.MessageFormat;

public class StopWatch {

    private long startNanoTime = System.nanoTime();
    private long nanoSeconds;

    public long getNanoSeconds() {
        return System.nanoTime() - startNanoTime;
    }

    public double getSeconds() {
        return TimeUtils.nanoSecondsToSeconds(getNanoSeconds());
    }

    public String formatSeconds() {
        return MessageFormat.format("{0,number,#.##}", getSeconds());
    }
}
