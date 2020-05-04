public class JuiceFountain {
  synchronized public void use(Worker worker) {
    System.out.println("Worker " + worker.id + " using fountain");
    try {
      Thread.sleep(1000);
    } catch (Exception e) {};
  };
};
