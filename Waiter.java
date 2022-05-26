public class Waiter extends Worker {
  // constructor
  Waiter(int id, Clock clock, Cupboard cupboard, JuiceFountain juiceFountain, int COOLDOWN_INTERVAL) {
    super(id, clock, cupboard, juiceFountain, COOLDOWN_INTERVAL);
    setName("Waiter " + id);
    setPriority(9);
  };

  @Override
  public void run() {
    synchronized (clock) {
      try {
        // wait for last order time
        clock.wait();
        // wait for closing time
        clock.wait();
      } catch (Exception e) {};
    };

    // finish current serve and prevent future serve
    orderLock.lock();
    // leave café
    System.out.println(getName() + " left");
    // set as no longer working
    working = false;
  };
};
