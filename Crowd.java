import java.util.concurrent.ThreadLocalRandom;

public class Crowd extends Thread {
  Clock clock;
  Table table;
  Worker[] workers;
  Statistics stats;
  // blank final
  final int RATIO_CAPPUCCINO;
  final int RATIO_FRUIT_JUICE;
  // initialise
  int id = 1;

  // constructor
  Crowd(Clock clock, Table table, Worker[] workers, Statistics stats, int RATIO_CAPPUCCINO, int RATIO_FRUIT_JUICE) {
    setName("Crowd");
    this.clock = clock;
    this.table = table;
    this.workers = workers;
    this.stats = stats;
    this.RATIO_CAPPUCCINO = RATIO_CAPPUCCINO;
    this.RATIO_FRUIT_JUICE = RATIO_FRUIT_JUICE;
  };

  @Override
  public void run() {
    while (!clock.isLastOrder()) {
      // random number of customers
      int numberOfCustomers = ThreadLocalRandom.current().nextInt(1, 6);
      // allocate seats for customers
      for (; numberOfCustomers > 0 && table.NUM_OF_SEAT.availablePermits() > 0; numberOfCustomers--, id++) {
        Customer customer = new Customer(clock, id, table, workers, stats, drinkRatio());
        table.takeSeat(customer);
        customer.start();
        System.out.println(customer.getName() + " is seated");
      };
      // announce that there are no seats left
      if (numberOfCustomers > 0) {
        System.out.println("No seats left");
        stats.addPotential(numberOfCustomers);
      };
      // random interval to next batch
      try {
        Thread.sleep(ThreadLocalRandom.current().nextInt(1, 6) * 500);
      } catch (Exception e) {};
    };
  };

  // set customer preference 
  public int drinkRatio() {
    // randomly select a number
    int rand = ThreadLocalRandom.current().nextInt(1, RATIO_FRUIT_JUICE + RATIO_CAPPUCCINO + 1);

    if (rand <= RATIO_CAPPUCCINO)
      // set order as cappuccino
      return 0;
    // set order as fruit juice
    return 1;
  };
};
