public class Customer extends Thread {
  int id;
  Boolean ordered = false;
  Seats seats;
  Waiter[] waiters;
  Owner owner;
  int drink;

  Customer(int id, Seats seats, Waiter[] waiters, Owner owner, int drink) {
    this.id = id;
    this.seats = seats;
    this.waiters = waiters;
    this.owner = owner;
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
      // try to ask owner to take order
      if (owner.takeOrder()) {
        ordered = true;
        owner.serveOrder(this);
      } else {
        // try to ask waiters to take order
        for (i = 0; i < waiters.length; i++)
          if (waiters[i].takeOrder()) {
            ordered = true;
            waiters[i].serveOrder(this);
            break;
          };
      };
    } while (!ordered);

    // leave seat
    seats.leaveSeat(this);
  };
};
