import java.util.Random;

public class Customers extends Thread {
  int id = 0;
  Clock clock;
  Seats seats;
  Waiter[] waiters;
  Owner owner;
  int drink = 0;
  int ratioCappuccino;
  int ratioFruitJuice;
  int cappuccinoCount = 0;
  int fruitJuiceCount = 0;

  Customers(Clock clock, Seats seats, Waiter[] waiters, Owner owner, int ratioCappuccino, int ratioFruitJuice) {
    this.clock = clock;
    this.seats = seats;
    this.waiters = waiters;
    this.owner = owner;
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
        Customer customer = new Customer(id, seats, waiters, owner, drinkRatio());
        seats.takeSeat(customer);
        customer.start();
      };
      // announce that there are no seats left
      if (numberOfCustomers > 0) {
        System.out.println("No seats left");
      };
      // random interval to next batch
      try {
        Thread.sleep((new Random().nextInt(5) + 1) * 1000);
      } catch (Exception e) {};
    };
  };

  public int drinkRatio() {
    if (drink == 0) {
      // serve cappuccino
      if (++cappuccinoCount == ratioCappuccino && ratioFruitJuice > 0) {
        // reset cappuccino count if ratio is met
        cappuccinoCount = 0;
        // set to serve fruit juice next
        drink = 1;
      };
      return 0;
    } else {
      // serve fruit juice
      if (++fruitJuiceCount == ratioFruitJuice && ratioFruitJuice > 0) {
        // reset fruit juice count if ratio is met
        fruitJuiceCount = 0;
        // set to serve cappuccino next
        drink = 0;
      };
      return 1;
    }
  };
};
