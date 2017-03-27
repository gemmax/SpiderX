package util;

/**
 * Created by kalexjune on 17/3/21.
 *
 */
public class Timer {
    private static long startTime = 0;
    private static long endTime = 0;

    private Timer() {}

    public synchronized static void start() {
        startTime = System.currentTimeMillis();
    }

    public synchronized static void end() {
        endTime = System.currentTimeMillis();
    }

    public synchronized static long getRunTime() {
        // System.out.println("start time: " + startTime + "  end time: " + endTime);
        return endTime - startTime;
    }

}
