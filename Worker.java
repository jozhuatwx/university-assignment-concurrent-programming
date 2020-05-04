import java.util.concurrent.locks.ReentrantLock;

public class Worker extends Thread {
  int id;
  int waitTime = 1;
  int waitInterval = 5;
  ReentrantLock lock = new ReentrantLock();
  Clock clock;
  Cup cup;
  Glass glass;
  Coffee coffee;
  JuiceFountain juiceFountain;
  Milk milk;

  Worker(int id, Clock clock, Cup cup, Glass glass, Coffee coffee, JuiceFountain juiceFountain, Milk milk) {
    this.id = id;
    this.clock = clock;
    this.cup = cup;
    this.glass = glass;
    this.coffee = coffee;
    this.juiceFountain = juiceFountain;
    this.milk = milk;
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
          System.out.println("Worker " + id + " served Customer " + customer.id + " cappuccino");
          break;

          // fruit juice
        case 1:
          serveFruitJuice();
          System.out.println("Worker " + id + " served Customer " + customer.id + " fruit juice");
          break;
      
        default:
          break;
      };

      lock.unlock();
    } catch (Exception e) {};
  };

  public void serveCappuccino() {
    Boolean checkCup = false, checkCoffee = false, checkMilk = false;
    // take cup
    do
      checkCup = cup.check();
    while (!checkCup);
    cup.take();

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
      checkCoffee = coffee.check();
      checkMilk = milk.check();
      // if (checkCoffee)
      //   System.out.println("Worker " + id + " took coffee");
      // if (checkMilk)
      //   System.out.println("Worker " + id + " took milk");
  
      if (checkCoffee && checkMilk) {
        // increase wait time
        waitTime += waitInterval;
        break;
      } else if (checkCoffee) {
        // release unused ingredient
        coffee.release();
      } else if (checkMilk) {
        // release unused ingredient
        milk.release();
      };

      // decrease wait time
      if (waitTime > waitInterval)
          waitTime -= waitInterval;
    } while (!checkCoffee || !checkMilk);

    coffee.use();
    milk.use();
    coffee.release();
    milk.release();

    // mixing drink
    //System.out.println("Worker " + id + " mixing drink");
    try {
      Thread.sleep(1000);
    } catch (Exception e) {};
  };

  public void serveFruitJuice() {
    Boolean checkGlass = false, checkFountain = false;
    // take glass
    do
      checkGlass = glass.check();
    while (!checkGlass);
    glass.take();

    // use juice fountain
    do
      checkFountain = juiceFountain.check();
    while (!checkFountain);
    juiceFountain.use();
    juiceFountain.release();

    // fill the glass
    //System.out.println("Worker " + id + " filling glass");
    try {
      Thread.sleep(1000);
    } catch (Exception e) {};
  };
};
