public class Waiter extends Worker {
  Boolean working = true;
  Waiter(int id, Clock clock, Cup cup, Glass glass, Coffee coffee, JuiceFountain juiceFountain, Milk milk) {
    super(id, clock, cup, glass, coffee, juiceFountain, milk);
    this.setPriority(9);
  };

  @Override
  public void run() {
    while (!clock.isClosing()) {
      synchronized (clock) {
        try {
          // wait for last call
          clock.wait();
          // wait for closing
          clock.wait();
          // finish current serve and prevent future serve
          lock.lock();
          // leave café
          System.out.println("Worker " + id + " left");
          // set as no longer working
          working = false;
        } catch (Exception e) {};
      };
    }
  };

  public Boolean isWorking() {
    return working;
  };
};
