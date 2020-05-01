import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Worker extends Thread {
  int id;
  ReentrantLock lock = new ReentrantLock();
  Clock clock;

  Worker(int id, Clock clock) {
    this.id = id;
    this.clock = clock;
  };

  // take customers' order
  public Boolean takeOrder() {
    return lock.tryLock();
  };

  // serve customers' order
  public void serveOrder(Customer customer) {
    try {
      // random serve time
      Thread.sleep((new Random().nextInt(3) + 1) * 1000);
      System.out.println("Worker " + id + " served Customer " + customer.id);
      lock.unlock();
    } catch (Exception e) {};
  };
};
