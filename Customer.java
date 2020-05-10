import java.util.concurrent.ThreadLocalRandom;

public class Customer extends Thread {
  Clock clock;
  Table table;
  Worker[] workers;
  Statistics stats;
  // blank final
  final int ORDER;
  // initialise
  Boolean ordered = false;

  // constructor
  Customer(Clock clock, int id, Table table, Worker[] workers, Statistics stats, int ORDER) {
    setName("Customer " + id);
    this.clock = clock;
    this.table = table;
    this.workers = workers;
    this.stats = stats;
    this.ORDER = ORDER;
  };

  @Override
  public void run() {
    // set start time
    long startTime = System.nanoTime();
    long endTime = 0;
    int index;
    int i, totalWorkers;
    // wait for turn
    do {
      // count number of working workers
      for (i = 1, totalWorkers = 1; i < workers.length; i++)
        if (workers[i].isWorking())
          totalWorkers++;
      // check if customer is next to be served
      index = table.customers.indexOf(this);
      if (index >= 0 && index < totalWorkers)
        break;
    } while (!ordered);

    // ask to order
    do {
      // check if it is past closing time
      if (clock.isClosing()) {
        // increment the number of customers unserved
        stats.addUnserved();
        break;
      };

      // try to ask workers to take order
      for (i = 0; i < workers.length; i++)
        if (workers[i].takeOrder()) {
          System.out.println(getName() + " ordered " + orderName(ORDER) + " from " + workers[i].getName());
          ordered = true;
          // asks worker to serve order
          workers[i].serveOrder(this);
          // random drinking time
          System.out.println(getName() + " is drinking " + orderName(ORDER));
          try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1, 6) * 500);
          } catch (Exception e) {};
          // set end time
          endTime = System.nanoTime();
          break;
        };
    } while (!ordered);

    // leave seat
    System.out.println(getName() + " left");
    table.leaveSeat(this);
    // statistics
    if (ordered) {
      // count elapsed time
      stats.addElapsedTime(endTime - startTime);
      // increment the number of customers served
      stats.addServed();
    };
  };

  public String orderName(int order) {
    switch (order) {
      // cappuccino
      case 0:
        return "cappucino";
    
        // fruit juice
      default:
        return "fruit juice";
    }
  };
};
