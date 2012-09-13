package foo.bar.util;

public final class TimeUtils {

    private TimeUtils() { }

    public static double nanoSecondsToSeconds(final long nanoSeconds) {
        return nanoSeconds / Math.pow(10d, 9);
    }

}
