public class Customer extends Thread {
  Boolean ordered = false;
  Clock clock;
  Seats seats;
  Waiter[] waiters;
  Owner owner;
  int drink;
  Statistics stats;

  // constructor
  Customer(Clock clock, int id, Seats seats, Waiter[] waiters, Owner owner, Statistics stats, int drink) {
    setName("Customer " + id);
    this.clock = clock;
    this.seats = seats;
    this.waiters = waiters;
    this.owner = owner;
    this.stats = stats;
    this.drink = drink;
  };

  @Override
  public void run() {
    int index;
    int i, totalWorkers;
    // wait for turn
    do {
      index = seats.customers.indexOf(this);
      for (i = 0, totalWorkers = 1; i < waiters.length; i++)
        if (waiters[i].isWorking())
          totalWorkers++;
      // check if customer is next to be served
      if (index >= 0 && index < totalWorkers) {
        break;
      };
    } while (!ordered);

    // ask to order
    do {
      if (clock.isClosing()) {
        stats.numberOfUnserved++;
        break;
      };

      // try to ask owner to take order
      if (owner.takeOrder()) {
        System.out.println(getName() + " ordered " + drinkName(drink) + " from " + owner.getName());
        ordered = true;
        stats.numberOfServed++;
        owner.serveOrder(this);
      } else {
        // try to ask waiters to take order
        for (i = 0; i < waiters.length; i++)
          if (waiters[i].takeOrder()) {
            System.out.println(getName() + " ordered " + drinkName(drink) + " from " + waiters[i].getName());
            ordered = true;
            stats.numberOfServed++;
            waiters[i].serveOrder(this);
            break;
          };
      };
    } while (!ordered);

    // leave seat
    System.out.println(getName() + " left");
    seats.leaveSeat(this);
  };

  public String drinkName(int drink) {
    switch (drink) {
      case 0:
        return "cappucino";
    
      default:
        return "fruit juice";
    }
  };
};
