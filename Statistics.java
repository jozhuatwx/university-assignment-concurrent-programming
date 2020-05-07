public class Statistics {
  int numberOfServed = 0;
  int numberOfUnserved = 0;
  int numberOfPotential = 0;
  long totalElapsedTime = 0;

  // add served
  synchronized public void addServed() {
    numberOfServed++;
  };

  // add unserved
  synchronized public void addUnserved() {
    numberOfUnserved++;
  };

  // add potential
  synchronized public void addPotential(int potential) {
    numberOfPotential += potential;
  };

  // add elapsed time
  synchronized public void addElapsedTime(long elapsedTime) {
    totalElapsedTime += elapsedTime;
  };
};
