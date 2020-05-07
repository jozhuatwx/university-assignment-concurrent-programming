import java.util.Random;

public class Customer extends Thread {
  Boolean ordered = false;
  Clock clock;
  Seats seats;
  Worker[] workers;
  int drink;
  Statistics stats;

  // constructor
  Customer(Clock clock, int id, Seats seats, Worker[] workers, Statistics stats, int drink) {
    setName("Customer " + id);
    this.clock = clock;
    this.seats = seats;
    this.workers = workers;
    this.stats = stats;
    this.drink = drink;
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
      index = seats.customers.indexOf(this);
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
          System.out.println(getName() + " ordered " + drinkName(drink) + " from " + workers[i].getName());
          ordered = true;
          // asks worker to serve order
          workers[i].serveOrder(this);
          // random drinking time
          System.out.println(getName() + " is drinking " + drinkName(drink));
          try {
            Thread.sleep((new Random().nextInt(5) + 1) * 500);
          } catch (Exception e) {};
          // set end time
          endTime = System.nanoTime();
          break;
        };
    } while (!ordered);

    // leave seat
    System.out.println(getName() + " left");
    seats.leaveSeat(this);
    // statistics
    if (ordered) {
      // count elapsed time
      stats.addElapsedTime(endTime - startTime);
      // increment the number of customers served
      stats.addServed();
    };
  };

  public String drinkName(int drink) {
    switch (drink) {
      // cappuccino
      case 0:
        return "cappucino";
    
        // fruit juice
      default:
        return "fruit juice";
    }
  };
};
