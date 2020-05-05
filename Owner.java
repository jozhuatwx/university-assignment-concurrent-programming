public class Owner extends Worker {
  Seats seats;
  Waiter[] waiters;

  // constructor
  Owner(Clock clock, Seats seats, Cupboard cupboard, JuiceFountain juiceFountain, Waiter[] waiters) {
    super(0, clock, cupboard, juiceFountain);
    setName("Owner");
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
        System.out.println(getName() + ": Last call!");
        // wait for closing time
        clock.wait();
        System.out.println(getName() + ": Closing time!");
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
    System.out.println(getName() + " left");
  };
};
