public class Owner extends Worker {
  Seats seats;
  Waiter[] waiters;

  Owner(Clock clock, Seats seats, Cup cup, Glass glass, Coffee coffee, JuiceFountain juiceFountain, Milk milk, Waiter[] waiters) {
    super(0, clock, cup, glass, coffee, juiceFountain, milk);
    this.seats = seats;
    this.waiters = waiters;
    this.setPriority(Thread.MAX_PRIORITY);
  };

  @Override
  public void run() {
    synchronized (clock) {
      try {
        // wait for last call
        clock.wait();
        System.out.println("Last call!");
      } catch (Exception e) {};
    };

    // check if there are still customers
    while (seats.hasCustomers()) {
      try {
        Thread.sleep(1);
      } catch (Exception e) {};
    };

    // check if all waiters have left
    for (int i = 0; i < waiters.length; i++) {
      while (waiters[i].isWorking()) {
        try {
          Thread.sleep(1);
        } catch (Exception e) {};
      };
    };
    
    // leave café
    System.out.println("Worker " + id + " left");
  };
};
