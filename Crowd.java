import java.util.Random;

public class Crowd extends Thread {
  int id = 1;
  Clock clock;
  Table table;
  Worker[] workers;
  Statistics stats;
  int ratioCappuccino;
  int ratioFruitJuice;

  // constructor
  Crowd(Clock clock, Table table, Worker[] workers, Statistics stats, int ratioCappuccino, int ratioFruitJuice) {
    this.setName("Customers");
    this.clock = clock;
    this.table = table;
    this.workers= workers;
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
      for (; numberOfCustomers > 0 && table.seat.availablePermits() > 0; numberOfCustomers--, id++) {
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
    // set order as fruit juice
    return 1;
  };
};
