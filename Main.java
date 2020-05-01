public class Main {
  public static void main(String[] args) {
    // threads
    // set last call time and closing time
    Clock clock = new Clock(10, 15);
    // set number of seats
    Seats seats = new Seats(clock, 10);
    // set number of waiters
    int numberOfWaiters = 2;
    Waiter waiters[] = new Waiter[numberOfWaiters];
    for (int i = 0; i < waiters.length;)
      waiters[i] = new Waiter(++i, clock);
    Owner owner = new Owner(clock, seats, waiters);
    Customers customers = new Customers(clock, seats, waiters, owner);

    clock.start();
    for (int i = 0; i < waiters.length; i++)
      waiters[i].start();
    owner.start();
    customers.start();
  };
};
