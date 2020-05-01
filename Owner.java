public class Owner extends Worker {
  Seats seats;
  Waiter[] waiters;

  Owner(Clock clock, Seats seats, Waiter[] waiters) {
    super(0, clock);
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
        // synchronized (this) {
        //   this.notifyAll();
        // };
        System.out.println("Last call!");
      } catch (Exception e) {};
    };

    // check if there are still customers
    while (true) {
      if (!seats.hasCustomers()) {
        break;
      };
    };

    // check if all waiters have left
    for (int i = 0; i < waiters.length; i++) {
      while (true) {
        if (!waiters[i].isWorking()) {
          break;
        } else {
          try {
            Thread.sleep(1);
          } catch (Exception e) {};
        };
      };
    };
    
    // leave café
    System.out.println("Worker " + id + " left");
  };
};
