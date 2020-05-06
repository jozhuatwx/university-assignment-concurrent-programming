import java.util.Random;
import java.util.concurrent.Semaphore;

public class JuiceFountain {
  Semaphore tap;

  // constructor
  JuiceFountain(int numberOfTaps) {
    this.tap = new Semaphore(numberOfTaps);
  };

  // tap
  public Boolean openTap() {
    // open the tap
    if (tap.availablePermits() > 0) {
      try {
        tap.acquire();
        Thread.sleep((new Random().nextInt(4) + 2) * 100);
      } catch (Exception e) {};
      return true;
    };
    return false;
  };

  public void closeTap() {
    try {
      Thread.sleep((new Random().nextInt(4) + 2) * 100);
    } catch (Exception e) {};
    // close the tap
    tap.release();
  };
};
