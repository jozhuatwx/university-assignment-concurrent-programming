public class Main {
  public static void main(String[] args) {
    // ingredients
    Cup cup = new Cup();
    Glass glass = new Glass();
    Coffee coffee = new Coffee();
    JuiceFountain juiceFountain = new JuiceFountain();
    Milk milk = new Milk();

    // set last call time and closing time
    Clock clock = new Clock(10, 15);
    // set number of seats
    Seats seats = new Seats(clock, 10);
    // set number of waiters
    int numberOfWaiters = 1;
    Waiter waiters[] = new Waiter[numberOfWaiters];
    for (int i = 0; i < waiters.length;)
      waiters[i] = new Waiter(++i, clock, cup, glass, coffee, juiceFountain, milk);
    Owner owner = new Owner(clock, seats, cup, glass, coffee, juiceFountain, milk, waiters);
    // set ratio of cappuccino against fruit juice
    Customers customers = new Customers(clock, seats, waiters, owner, 1, 0);

    clock.start();
    for (int i = 0; i < waiters.length; i++)
      waiters[i].start();
    owner.start();
    customers.start();
  };
};
