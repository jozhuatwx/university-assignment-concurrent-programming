public class Customer extends Thread {
  int id;
  Boolean ordered = false;
  Seats seats;
  Waiter[] waiters;
  Owner owner;

  Customer(int id, Seats seats, Waiter[] waiters, Owner owner) {
    this.id = id;
    this.seats = seats;
    this.waiters = waiters;
    this.owner = owner;
  };

  @Override
  public void run() {
    int index = -1;
    int totalWorkers = waiters.length + 1;
    // wait for turn
    while (!ordered) {
      index = seats.customers.indexOf(this);
      // check if customer is next to be served
      if (index >= 0 && index < totalWorkers) {
        break;
      };
    };

    // ask to order
    while (!ordered) {
      // try to ask owner to take order
      if (owner.takeOrder()) {
        ordered = true;
        owner.serveOrder(this);
      } else {
        // try to ask waiters to take order
        for (int i = 0; i < waiters.length; i++)
          if (waiters[i].takeOrder()) {
            ordered = true;
            waiters[i].serveOrder(this);
            break;
          };
      };
    };

    // leave seat
    seats.leaveSeat(this);
  };
};
