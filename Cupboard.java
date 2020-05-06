import java.util.Random;
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
    try {
      Thread.sleep((new Random().nextInt(3) + 1) * 100);
    } catch (Exception e) {};
  };

  public void close() {
    try {
      Thread.sleep((new Random().nextInt(3) + 1) * 100);
    } catch (Exception e) {};
    // close the cupboard
    lock.unlock();
  };

  // cup
  public void takeCup() {
    // take a cup
    try {
      Thread.sleep((new Random().nextInt(5) + 3) * 100);
    } catch (Exception e) {};
  };

  // glass
  public void takeGlass() {
    // take a glass
    try {
      Thread.sleep((new Random().nextInt(5) + 3) * 100);
    } catch (Exception e) {};
  };

  // coffee
  public Boolean takeCoffee() {
    // take coffee
    if (coffee.availablePermits() > 0) {
      try {
        coffee.acquire();
        Thread.sleep((new Random().nextInt(5) + 3) * 100);
    } catch (Exception e) {};
      return true;
    };
    return false;
  };

  public void returnCoffee() {
    // return coffee
    try {
      Thread.sleep((new Random().nextInt(5) + 3) * 100);
    } catch (Exception e) {};
    coffee.release();
  };

  // milk
  public Boolean takeMilk() {
    // take milk
    if (milk.availablePermits() > 0) {
      try {
        milk.acquire();
        Thread.sleep((new Random().nextInt(5) + 3) * 100);
      } catch (Exception e) {};
      return true;
    };
    return false;
  };

  public void returnMilk() {
    // return milk
    try {
      Thread.sleep((new Random().nextInt(5) + 3) * 100);
    } catch (Exception e) {};
    milk.release();
  };
};
