import java.util.ListIterator;

public class Owner extends Worker {
  Table table;
  Waiter[] waiters;

  // constructor
  Owner(Clock clock, Table table, Cupboard cupboard, JuiceFountain juiceFountain, Waiter[] waiters, int COOLDOWN_INTERVAL) {
    super(0, clock, cupboard, juiceFountain, COOLDOWN_INTERVAL);
    setName("Owner");
    setPriority(Thread.MAX_PRIORITY);
    this.table = table;
    this.waiters = waiters;
  };

  @Override
  public void run() {
    synchronized (clock) {
      try {
        // wait for last order time
        clock.wait();
      } catch (Exception e) {};
    };
    System.out.println(getName() + ": Last order!");

    synchronized (clock) {
      try {
        // wait for closing time
        clock.wait();
      } catch (Exception e) {};
    };
    System.out.println(getName() + ": Closing!");

    // finish current serve and prevent future serve
    orderLock.lock();

    // wait for all waiters to leave
    for (int i = 0; i < waiters.length; i++)
      try {
        waiters[i].join();
      } catch (Exception e) {};

    // wait for all customers to leave
    ListIterator<Customer> iterator = table.customers.listIterator();
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
