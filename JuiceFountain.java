import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class JuiceFountain {
  // blank final
  final Semaphore NUM_OF_FOUNTAIN_TAP;

  // constructor
  JuiceFountain(int NUM_OF_FOUNTAIN_TAP) {
    this.NUM_OF_FOUNTAIN_TAP = new Semaphore(NUM_OF_FOUNTAIN_TAP);
  };

  // tap
  public Boolean openTap() {
    // open the tap
    if (NUM_OF_FOUNTAIN_TAP.availablePermits() > 0) {
      try {
        NUM_OF_FOUNTAIN_TAP.acquire();
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
    NUM_OF_FOUNTAIN_TAP.release();
  };
};
