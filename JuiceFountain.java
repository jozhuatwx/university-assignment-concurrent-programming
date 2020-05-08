import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class JuiceFountain {
  Semaphore tap;

  // constructor
  JuiceFountain(int NUM_OF_FOUNTAIN_TAP) {
    this.tap = new Semaphore(NUM_OF_FOUNTAIN_TAP);
  };

  // tap
  public Boolean openTap() {
    // open the tap
    if (tap.availablePermits() > 0) {
      try {
        tap.acquire();
        Thread.sleep(ThreadLocalRandom.current().nextInt(2, 5) * 100);
      } catch (Exception e) {};
      return true;
    };
    return false;
  };

  public void closeTap() {
    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(2, 5) * 100);
    } catch (Exception e) {};
    // close the tap
    tap.release();
  };
};
