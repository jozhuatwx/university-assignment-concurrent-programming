public class Waiter extends Worker {
  // constructor
  Waiter(int id, Clock clock, Cupboard cupboard, JuiceFountain juiceFountain) {
    super(id, clock, cupboard, juiceFountain);
    setName("Waiter " + id);
    this.setPriority(9);
  };

  @Override
  public void run() {
    synchronized (clock) {
      try {
        // wait for last order time
        clock.wait();
        // wait for closing time
        clock.wait();
        // finish current serve and prevent future serve
        lock.lock();
        // leave café
        System.out.println(getName() + " left");
        // set as no longer working
        working = false;
      } catch (Exception e) {};
    };
  };
};
