import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Statistics {
  // initialise
  AtomicInteger numberOfServed = new AtomicInteger(0);
  AtomicInteger numberOfUnserved = new AtomicInteger(0);
  AtomicInteger numberOfPotential = new AtomicInteger(0);
  AtomicLong totalElapsedTime = new AtomicLong(0);

  // add served
  public void addServed() {
    numberOfServed.incrementAndGet();
  };

  // add unserved
  public void addUnserved() {
    numberOfUnserved.incrementAndGet();
  };

  // add potential
  public void addPotential(int potential) {
    numberOfPotential.addAndGet(potential);
  };

  // add elapsed time
  public void addElapsedTime(long elapsedTime) {
    totalElapsedTime.addAndGet(elapsedTime);
  };
};
