import java.util.Random;

public class Customers extends Thread {
  int id = 0;
  Clock clock;
  Seats seats;
  Waiter[] waiters;
  Owner owner;

  Customers(Clock clock, Seats seats, Waiter[] waiters, Owner owner) {
    this.clock = clock;
    this.seats = seats;
    this.waiters = waiters;
    this.owner = owner;
  };

  @Override
  public void run() {
    while (!clock.isLastOrder()) {
      // random number of customers
      int numberOfCustomers = (new Random().nextInt(5) + 1);
      // allocate seats for customers
      for (; numberOfCustomers > 0 && seats.seat.availablePermits() > 0; numberOfCustomers--, id++) {
        Customer customer = new Customer(id, seats, waiters, owner);
        seats.takeSeat(customer);
        customer.start();
      };
      // announce that there are no seats left
      if (numberOfCustomers > 0) {
        System.out.println("No seats left for new customers");
      };
      // random interval to next batch
      try {
        Thread.sleep((new Random().nextInt(5) + 1) * 1000);
      } catch (Exception e) {};
    };
  }
};
