import java.util.Random;

public class Customers extends Thread {
  int id = 1;
  Clock clock;
  Seats seats;
  Waiter[] waiters;
  Owner owner;
  Statistics stats;
  int drink = 0;
  int ratioCappuccino;
  int ratioFruitJuice;

  // constructor
  Customers(Clock clock, Seats seats, Waiter[] waiters, Owner owner, Statistics stats, int ratioCappuccino, int ratioFruitJuice) {
    this.setName("Customers");
    this.clock = clock;
    this.seats = seats;
    this.waiters = waiters;
    this.owner = owner;
    this.stats = stats;
    this.ratioCappuccino = ratioCappuccino;
    this.ratioFruitJuice = ratioFruitJuice;
  };

  @Override
  public void run() {
    while (!clock.isLastOrder()) {
      // random number of customers
      int numberOfCustomers = (new Random().nextInt(5) + 1);
      // allocate seats for customers
      for (; numberOfCustomers > 0 && seats.seat.availablePermits() > 0; numberOfCustomers--, id++) {
        Customer customer = new Customer(clock, id, seats, waiters, owner, stats, drinkRatio());
        seats.takeSeat(customer);
        customer.start();
        System.out.println(customer.getName() + " is seated");
      };
      // announce that there are no seats left
      if (numberOfCustomers > 0) {
        System.out.println("No seats left");
        stats.numberOfPotential += numberOfCustomers;
      };
      // random interval to next batch
      try {
        Thread.sleep((new Random().nextInt(5) + 1) * 500);
      } catch (Exception e) {};
    };
  };

  // set customer preference 
  public int drinkRatio() {
    // randomly select a number
    int rand = new Random().nextInt(ratioFruitJuice + ratioCappuccino) + 1;

    if (rand <= ratioCappuccino)
      // set order as cappuccino
      return 0;
    else
      // set order as fruit juice
      return 1;
  };
};
