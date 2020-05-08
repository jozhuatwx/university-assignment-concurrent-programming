import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class Cupboard {
  // initialise
  ReentrantLock lock = new ReentrantLock();

  Semaphore coffee;
  Semaphore milk;

  // constructor
  Cupboard(int NUM_OF_COFFEE, int NUM_OF_MILK) {
    this.coffee = new Semaphore(NUM_OF_COFFEE);
    this.milk = new Semaphore(NUM_OF_MILK);
  };

  // cupboard
  public void open() {
    // open the cupboard
    lock.lock();
    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(1, 4) * 100);
    } catch (Exception e) {};
  };

  public void close() {
    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(1, 4) * 100);
    } catch (Exception e) {};
    // close the cupboard
    lock.unlock();
  };

  // cup
  public void takeCup() {
    // take a cup
    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(3, 6) * 100);
    } catch (Exception e) {};
  };

  // glass
  public void takeGlass() {
    // take a glass
    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(3, 6) * 100);
    } catch (Exception e) {};
  };

  // coffee
  public Boolean takeCoffee() {
    // take coffee
    if (coffee.availablePermits() > 0) {
      try {
        coffee.acquire();
        Thread.sleep(ThreadLocalRandom.current().nextInt(3, 6) * 100);
    } catch (Exception e) {};
      return true;
    };
    return false;
  };

  public void returnCoffee() {
    // return coffee
    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(3, 6) * 100);
    } catch (Exception e) {};
    coffee.release();
  };

  // milk
  public Boolean takeMilk() {
    // take milk
    if (milk.availablePermits() > 0) {
      try {
        milk.acquire();
        Thread.sleep(ThreadLocalRandom.current().nextInt(3, 6) * 100);
      } catch (Exception e) {};
      return true;
    };
    return false;
  };

  public void returnMilk() {
    // return milk
    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(3, 6) * 100);
    } catch (Exception e) {};
    milk.release();
  };
};
