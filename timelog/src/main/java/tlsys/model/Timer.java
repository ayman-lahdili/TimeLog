package tlsys.model;

import java.time.Duration;
import java.time.Instant;

public class Timer {

    private Instant startTime;
    private Instant endTime;

    public Instant start() {
        return startTime = Instant.now();
    }

    public Instant stop() {
        return endTime = Instant.now();
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public Duration getDuration() {
        if (startTime == null || endTime == null) {
            throw new IllegalStateException("Timer not started or stopped.");
        }
        return Duration.between(startTime, endTime);
    }
    
    public double getDurationInHours() {
        Duration duration = getDuration();
        return duration.toNanos() / (3600.0 * 1e9);
    }

    public void printDuration() {
        Duration duration = getDuration();
        long seconds = duration.getSeconds();
        long millis = duration.toMillis() % 1000;
        System.out.printf("Elapsed time: %d seconds and %d milliseconds.%n", seconds, millis);
    }

}
