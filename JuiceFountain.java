public class JuiceFountain {
  // only one worker can access
  synchronized public void use() {
    // use the fountain
    try {
      Thread.sleep(1000);
    } catch (Exception e) {};
  };
};
