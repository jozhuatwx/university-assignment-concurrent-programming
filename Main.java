public class Main {
  public static void main(String[] args) {
    // configurations
    // set number of each ingredient
    final int NUM_OF_COFFEE = 1;
    final int NUM_OF_MILK = 1;
    final int NUM_OF_FOUNTAIN_TAP = 1;
    // set last order and closing time
    final int LAST_CALL_TIME = 15;
    final int CLOSING_TIME = 20;
    // set number of waiters
    final int NUM_OF_WAITER = 1;
    // set number of seats
    final int NUM_OF_SEAT = 10;
    // set ratio of cappuccino against fruit juice
    final int RATIO_CAPPUCCINO = 1;
    final int RATIO_FRUIT_JUICE = 1;

    // initialise
    Statistics stats = new Statistics();
    Cupboard cupboard = new Cupboard(NUM_OF_COFFEE, NUM_OF_MILK);
    JuiceFountain juiceFountain = new JuiceFountain(NUM_OF_FOUNTAIN_TAP);
    Seats seats = new Seats(NUM_OF_SEAT);

    Clock clock = new Clock(LAST_CALL_TIME, CLOSING_TIME);
    Waiter waiters[] = new Waiter[NUM_OF_WAITER];
    for (int i = 0; i < waiters.length;)
      waiters[i] = new Waiter(++i, clock, cupboard, juiceFountain);
    Owner owner = new Owner(clock, seats, cupboard, juiceFountain, waiters);
    Worker workers[] = new Worker[NUM_OF_WAITER + 1];
    workers[0] = owner;
    for (int i = 0; i < waiters.length; i++)
      workers[i + 1] = waiters[i];
    Crowd crowd = new Crowd(clock, seats, workers, stats, RATIO_CAPPUCCINO, RATIO_FRUIT_JUICE);

    // start threads
    clock.start();
    for (int i = 0; i < workers.length; i++)
      workers[i].start();
    crowd.start();

    // wait for all threads to stop
    try {
      crowd.join();
      clock.join();
      for (int i = 0; i < workers.length; i++)
        workers[i].join();
    } catch (Exception e) {};

    // print statistics
    System.out.println("\nTotal customers served: " + stats.numberOfServed);
    System.out.println("Average elapsed time: " + ((stats.totalElapsedTime / stats.numberOfServed) / 1000000) + "ms");
    System.out.println("\nTotal customers unserved: " + stats.numberOfUnserved);
    System.out.println("Total customers left without a seat: " + stats.numberOfPotential);
  };
};
