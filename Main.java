public class Main {
  public static void main(String[] args) {
    // ingredients
    Cupboard cupboard = new Cupboard();
    JuiceFountain juiceFountain = new JuiceFountain();

    // set last call time and closing time
    Clock clock = new Clock(15, 20);
    // set number of seats
    Seats seats = new Seats(clock, 10);
    // set number of waiters
    int numberOfWaiters = 1;
    Waiter waiters[] = new Waiter[numberOfWaiters];
    for (int i = 0; i < waiters.length;)
      waiters[i] = new Waiter(++i, clock, cupboard, juiceFountain);
    Owner owner = new Owner(clock, seats, cupboard, juiceFountain, waiters);
    // set ratio of cappuccino against fruit juice
    Customers customers = new Customers(clock, seats, waiters, owner, 2, 2);

    clock.start();
    for (int i = 0; i < waiters.length; i++)
      waiters[i].start();
    owner.start();
    customers.start();
  };
};
