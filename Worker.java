import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class Worker extends Thread {
  Clock clock;
  Cupboard cupboard;
  JuiceFountain juiceFountain;
  // blank final
  final int COOLDOWN_INTERVAL;
  // initialise
  int id;
  int cooldown = 1;
  Boolean working = true;
  ReentrantLock orderLock = new ReentrantLock();

  // constructor
  Worker(int id, Clock clock, Cupboard cupboard, JuiceFountain juiceFountain, int COOLDOWN_INTERVAL) {
    this.id = id;
    this.clock = clock;
    this.cupboard = cupboard;
    this.juiceFountain = juiceFountain;
    this.COOLDOWN_INTERVAL = COOLDOWN_INTERVAL;
  };

  // take customers' order
  public Boolean takeOrder() {
    return orderLock.tryLock();
  };

  // serve customers' order
  public void serveOrder(Customer customer) {
    // random order
    switch (customer.ORDER) {
        // cappuccino
      case 0:
        serveCappuccino();
        System.out.println(getName() + " served " + customer.getName());
        break;

        // fruit juice
      case 1:
        serveFruitJuice();
        System.out.println(getName() + " served " + customer.getName());
        break;
    
      default:
        break;
    };
    orderLock.unlock();
  };

  // serve cappucino
  public void serveCappuccino() {
    Boolean checkCoffee = false, checkMilk = false;
    // take a cup
    cupboard.open();

    cupboard.takeCup();
    System.out.println(getName() + " took a cup");

    // take coffee and milk
    do {
      // reduce speed
      if (!clock.isLastOrder()) {
        try {
          // set wait time to prioritise number of executions and id
          Thread.sleep(cooldown + (id * 10));
        } catch (Exception e) {};
      };

      // check if ingredients are available
      if (!cupboard.cupboardLock.isHeldByCurrentThread())
        cupboard.open();

      checkCoffee = cupboard.takeCoffee();
      checkMilk = cupboard.takeMilk();

      if (checkCoffee)
        System.out.println(getName() + " took coffee");
      if (checkMilk)
        System.out.println(getName() + " took milk");

      if (checkCoffee && checkMilk) {
        cupboard.close();
        // increase wait time
        cooldown += COOLDOWN_INTERVAL;
        break;
      } else if (checkCoffee) {
        // release unused coffee
        System.out.println(getName() + " returned unused coffee");
        cupboard.returnCoffee();
      } else if (checkMilk) {
        // release unused milk
        System.out.println(getName() + " returned unused milk");
        cupboard.returnMilk();
      };
      cupboard.close();

      // decrease wait time
      if (cooldown > COOLDOWN_INTERVAL)
          cooldown -= COOLDOWN_INTERVAL;
    } while (!checkCoffee || !checkMilk);

    // pour ingredients
    System.out.println(getName() + " pouring ingredients");
    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(10, 21) * 100);
    } catch (Exception e) {};

    // return ingredients
    cupboard.open();

    cupboard.returnCoffee();
    System.out.println(getName() + " returned coffee");

    cupboard.returnMilk();
    System.out.println(getName() + " returned milk");

    cupboard.close();

    // mixing drink
    System.out.println(getName() + " mixing drink");
    try {
      Thread.sleep(500);
    } catch (Exception e) {};
  };

  // serve fruit juice
  public void serveFruitJuice() {
    Boolean checkTap = false;
    // take a glass
    cupboard.open();

    cupboard.takeGlass();
    System.out.println(getName() + " took a glass");

    cupboard.close();

    // use juice fountain
    do {
      // reduce speed
      if (!clock.isLastOrder()) {
        try {
          // set wait time to prioritise number of executions and id
          Thread.sleep(cooldown + (id * 10));
        } catch (Exception e) {};
      };

      checkTap = juiceFountain.openTap();
      if (checkTap)
        System.out.println(getName() + " opened juice fountain tap");

      // decrease wait time
      if (cooldown > COOLDOWN_INTERVAL)
      cooldown -= COOLDOWN_INTERVAL;
    } while (!checkTap);

    // use juice fountain
    try {
      // fill the glass
      System.out.println(getName() + " filling glass");
      Thread.sleep(ThreadLocalRandom.current().nextInt(10, 16) * 100);
    } catch (Exception e) {};

    System.out.println(getName() + " closed juice fountain tap");
    juiceFountain.closeTap();
  };

  // check if waiter is working
  public Boolean isWorking() {
    return working;
  };
};
