package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

public class StatStopwatch extends Stopwatch {
    private Logger log;
    private StringBuilder results = new StringBuilder();

    public StatStopwatch(Logger log) {
        this.log = log;
    }

    protected void finished(long nanos, Description description) {
        String result = String.format("\n%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
        results.append(result);
        log.info(result + " ms\n");
    }

    public String printResult() {
        return ("\n---------------------------------" +
                "\nTest                 Duration, ms" +
                "\n---------------------------------" +
                results +
                "\n---------------------------------");
    }
}

