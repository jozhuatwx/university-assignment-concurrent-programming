import java.util.concurrent.locks.ReentrantLock;

public class Coffee {
  ReentrantLock lock = new ReentrantLock();

  public Boolean check() {
    return lock.tryLock();
  };

  public void use() {
    try {
      Thread.sleep(1000);
    } catch (Exception e) {};
  };

  public void release() {
    //System.out.println("Worker " + worker.id + " release coffee");
    lock.unlock();
  };
};
