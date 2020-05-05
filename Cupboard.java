import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Cupboard {
  // initialise
  ReentrantLock lock = new ReentrantLock();

  Semaphore coffee;
  Semaphore milk;

  // constructor
  Cupboard(int numberOfCoffees, int numberOfMilks) {
    this.coffee = new Semaphore(numberOfCoffees);
    this.milk = new Semaphore(numberOfMilks);
  };

  // cupboard
  public void open() {
    // open the cupboard
    lock.lock();
  };

  public void close() {
    // close the cupboard
    lock.unlock();
  };

  // cup
  public void takeCup() {
    // take a cup
    try {
      Thread.sleep(1000);
    } catch (Exception e) {};
  };

  // glass
  public void takeGlass() {
    // take a glass
    try {
      Thread.sleep(1000);
    } catch (Exception e) {};
  };

  // coffee
  public Boolean takeCoffee() {
    // take coffee
    if (coffee.availablePermits() > 0) {
      try {
        coffee.acquire();
      } catch (Exception e) {};
      return true;
    };
    return false;
  };

  public void returnCoffee() {
    // return coffee
    coffee.release();
  };

  // milk
  public Boolean takeMilk() {
    // take milk
    if (milk.availablePermits() > 0) {
      try {
        milk.acquire();
      } catch (Exception e) {};
      return true;
    };
    return false;
  };

  public void returnMilk() {
    // return milk
    milk.release();
  };
};
