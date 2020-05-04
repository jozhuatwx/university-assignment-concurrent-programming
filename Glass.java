import java.util.concurrent.locks.ReentrantLock;

public class Glass {
  ReentrantLock lock = new ReentrantLock();

  public Boolean check() {
    return lock.tryLock();
  };

  public void take() {
    //System.out.println("Worker " + worker.id + " taking glass");
    try {
      Thread.sleep(1000);
    } catch (Exception e) {};
    lock.unlock();
  };
};
