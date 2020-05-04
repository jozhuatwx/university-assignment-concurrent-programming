import java.util.concurrent.locks.ReentrantLock;

public class JuiceFountain {
  ReentrantLock lock = new ReentrantLock();

  public Boolean check() {
    return lock.tryLock();
  };

  public void use() {
    //System.out.println("Worker " + worker.id + " using fountain");
    try {
      //Thread.sleep(2000);
    } catch (Exception e) {};
  };

  public void release() {
    //System.out.println("Worker " + worker.id + " using fountain");
    lock.unlock();
  };
};
