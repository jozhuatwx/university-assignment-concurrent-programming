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
    Boolean checkCoffee = false, checkMilk = false;
    // take cup
    cupboard.open(this);
    cupboard.takeCup(this);

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
        cupboard.open(this);
      checkCoffee = cupboard.takeCoffee(this);
      checkMilk = cupboard.takeMilk(this);
      if (checkCoffee)
        System.out.println("Worker " + id + " took coffee");
      if (checkMilk)
        System.out.println("Worker " + id + " took milk");
  
      if (checkCoffee && checkMilk) {
        cupboard.close(this);
        // increase wait time
        waitTime += waitInterval;
        break;
      } else if (checkCoffee) {
        // release unused ingredient
        cupboard.returnCoffee(this);
      } else if (checkMilk) {
        // release unused ingredient
        cupboard.returnMilk(this);
      };
      cupboard.close(this);

      // decrease wait time
      if (waitTime > waitInterval)
          waitTime -= waitInterval;
    } while (!checkCoffee || !checkMilk);

    // pour ingredients
    try {
      Thread.sleep(2000);
    } catch (Exception e) {};

    // return ingredients
    cupboard.open(this);
    cupboard.returnCoffee(this);
    cupboard.returnMilk(this);
    cupboard.close(this);

    // mixing drink
    //System.out.println("Worker " + id + " mixing drink");
    try {
      Thread.sleep(500);
    } catch (Exception e) {};
  };

  public void serveFruitJuice() {
    // take glass
    cupboard.open(this);
    cupboard.takeGlass(this);
    cupboard.close(this);

    // use juice fountain
    juiceFountain.use(this);

    // fill the glass
    //System.out.println("Worker " + id + " filling glass");
    try {
      Thread.sleep(500);
    } catch (Exception e) {};
  };
};
