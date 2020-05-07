public class Statistics {
  Integer numberOfServed = 0;
  Integer numberOfUnserved = 0;
  Integer numberOfPotential = 0;
  Long totalElapsedTime = (long) 0;

  // add served
  public void addServed() {
    synchronized (numberOfServed) {
      numberOfServed++;
    };
  };

  // add unserved
  public void addUnserved() {
    synchronized (numberOfUnserved) {
      numberOfUnserved++;
    };
  };

  // add potential
  public void addPotential(int potential) {
    synchronized (numberOfPotential) {
      numberOfPotential += potential;
    };
  };

  // add elapsed time
  public void addElapsedTime(long elapsedTime) {
    synchronized (totalElapsedTime) {
      totalElapsedTime += elapsedTime;
    };
  };
};
