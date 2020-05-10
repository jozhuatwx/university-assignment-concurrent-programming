import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class Cupboard {
  // blank final
  final Semaphore NUM_OF_COFFEE;
  final Semaphore NUM_OF_MILK;
  // initialise
  ReentrantLock cupboardLock = new ReentrantLock();

  // constructor
  Cupboard(int NUM_OF_COFFEE, int NUM_OF_MILK) {
    this.NUM_OF_COFFEE = new Semaphore(NUM_OF_COFFEE);
    this.NUM_OF_MILK = new Semaphore(NUM_OF_MILK);
  };

  // cupboard
  public void open() {
    // open the cupboard
    cupboardLock.lock();
    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(1, 4) * 100);
    } catch (Exception e) {};
  };

  public void close() {
    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(1, 4) * 100);
    } catch (Exception e) {};
    // close the cupboard
    cupboardLock.unlock();
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
    if (NUM_OF_COFFEE.availablePermits() > 0) {
      try {
        NUM_OF_COFFEE.acquire();
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
    NUM_OF_COFFEE.release();
  };

  // milk
  public Boolean takeMilk() {
    // take milk
    if (NUM_OF_MILK.availablePermits() > 0) {
      try {
        NUM_OF_MILK.acquire();
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
    NUM_OF_MILK.release();
  };
};
