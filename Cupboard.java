import java.util.concurrent.locks.ReentrantLock;

public class Cupboard {
  ReentrantLock lock = new ReentrantLock();
  ReentrantLock coffeeLock = new ReentrantLock();
  ReentrantLock milkLock = new ReentrantLock();

  public void open(Worker worker) {
    lock.lock();
    System.out.println("Worker " + worker.id + " opened cupboard");
  };

  public void close(Worker worker) {
    System.out.println("Worker " + worker.id + " closed cupboard");
    lock.unlock();
  };

  public void takeCup(Worker worker) {
    System.out.println("Worker " + worker.id + " takes cup");
    try {
      Thread.sleep(1000);
    } catch (Exception e) {};
  };

  public void takeGlass(Worker worker) {
    System.out.println("Worker " + worker.id + " takes glass");
    try {
      Thread.sleep(1000);
    } catch (Exception e) {};
  };

  public Boolean takeCoffee(Worker worker) {
    return coffeeLock.tryLock();
  };

  public void returnCoffee(Worker worker) {
    System.out.println("Worker " + worker.id + " returns coffee");
    coffeeLock.unlock();
  };

  public Boolean takeMilk(Worker worker) {
    return milkLock.tryLock();
  };

  public void returnMilk(Worker worker) {
    System.out.println("Worker " + worker.id + " returns milk");
    milkLock.unlock();
  };
};
