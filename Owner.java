import java.util.ListIterator;

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
        // wait for last order time
        clock.wait();
        System.out.println(getName() + ": Last order!");
        // wait for closing time
        clock.wait();
        System.out.println(getName() + ": Closing!");
        // finish current serve and prevent future serve
        lock.lock();
      } catch (Exception e) {};
    };

    // wait for all waiters to leave
    for (int i = 0; i < waiters.length; i++)
      try {
        waiters[i].join();
      } catch (Exception e) {};

    // wait for all customers to leave
    ListIterator<Customer> iterator = seats.customers.listIterator();
    while (iterator.hasNext())
      try {
        iterator.next().join();
      } catch (Exception e) {};
    
    // leave café
    System.out.println(getName() + " left");
    // set as no longer working
    working = false;
  };
};
