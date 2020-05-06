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
        stats.numberOfUnserved++;
        break;
      };

      // try to ask workers to take order
      for (i = 0; i < workers.length; i++)
        if (workers[i].takeOrder()) {
          System.out.println(getName() + " ordered " + drinkName(drink) + " from " + workers[i].getName());
          ordered = true;
          // asks worker to serve order
          workers[i].serveOrder(this);
          // increment the number of customers served
          stats.numberOfServed++;
          break;
        };
    } while (!ordered);

    // leave seat
    System.out.println(getName() + " left");
    seats.leaveSeat(this);
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
