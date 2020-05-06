import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Worker extends Thread {
  Boolean working = true;
  int id;
  int waitTime = 1;
  int waitInterval = 5;
  ReentrantLock lock = new ReentrantLock();
  Clock clock;
  Cupboard cupboard;
  JuiceFountain juiceFountain;

  // constructor
  Worker(int id, Clock clock, Cupboard cupboard, JuiceFountain juiceFountain) {
    this.id = id;
    this.clock = clock;
    this.cupboard = cupboard;
    this.juiceFountain = juiceFountain;
  };

  // take customers' order
  public Boolean takeOrder() {
    return lock.tryLock();
  };

  // serve customers' order
  public void serveOrder(Customer customer) {
    // random order
    switch (customer.drink) {
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
    
    lock.unlock();
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
      if (!clock.isLastOrder() || id != 0) {
        try {
          // set wait time to prioritise number of executions and id
          Thread.sleep(waitTime * id);
        } catch (Exception e) {};
      };

      // check if ingredients are available
      if (!cupboard.lock.isHeldByCurrentThread())
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
        waitTime += waitInterval;
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
      if (waitTime > waitInterval)
          waitTime -= waitInterval;
    } while (!checkCoffee || !checkMilk);

    // pour ingredients
    System.out.println(getName() + " pouring ingredients");
    try {
      Thread.sleep((new Random().nextInt(20) + 10) * 100);
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
      if (!clock.isLastOrder() || id != 0) {
        try {
          // set wait time to prioritise number of executions and id
          Thread.sleep(waitTime * id);
        } catch (Exception e) {};
      };

      checkTap = juiceFountain.openTap();
      if (checkTap)
        System.out.println(getName() + " opened juice fountain tap");

      // decrease wait time
      if (waitTime > waitInterval)
      waitTime -= waitInterval;
    } while (!checkTap);
    
    // use juice fountain
    try {
      Thread.sleep((new Random().nextInt(15) + 10) * 100);
    } catch (Exception e) {};
    System.out.println(getName() + " closed juice fountain tap");
    juiceFountain.closeTap();

    // fill the glass
    System.out.println(getName() + " filling glass");
    try {
      Thread.sleep(500);
    } catch (Exception e) {};
  };

  // check if waiter is working
  public Boolean isWorking() {
    return working;
  };
};
