import java.util.concurrent.locks.ReentrantLock;

public class Worker extends Thread {
  int id;
  int waitTime = 1;
  int waitInterval = 5;
  ReentrantLock lock = new ReentrantLock();
  Clock clock;
  Cupboard cupboard;
  JuiceFountain juiceFountain;

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
    try {
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
    } catch (Exception e) {};
  };

  // serve cappucino
  public void serveCappuccino() {
    Boolean checkCoffee = false, checkMilk = false;
    // take cup
    cupboard.open();

    cupboard.takeCup();
    System.out.println(getName() + " took a cup");

    // take coffee and milk
    do {
      // reduce speed
      if (!clock.isLastOrder() || id != 0) {
        try {
          // set wait time to prioritise number of executions and id
          Thread.sleep(waitTime * id * 3);
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
      Thread.sleep(2000);
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
    // take glass
    cupboard.open();

    cupboard.takeGlass();
    System.out.println(getName() + " took a glass");
    
    cupboard.close();

    // use juice fountain
    juiceFountain.use();
    System.out.println(getName() + " used the juice fountain");

    // fill the glass
    System.out.println(getName() + " filling glass");
    try {
      Thread.sleep(500);
    } catch (Exception e) {};
  };
};
