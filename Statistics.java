public class Statistics {
  int numberOfServed = 0;
  int numberOfUnserved = 0;
  int numberOfPotential = 0;
  long totalElapsedTime = 0;

  // add elapsed time
  synchronized public void addElapsedTime(long elapsedTime) {
    totalElapsedTime += elapsedTime;
  };
};
