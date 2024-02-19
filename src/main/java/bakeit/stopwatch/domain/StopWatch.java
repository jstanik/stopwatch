package bakeit.stopwatch.domain;

public class StopWatch {

  private long startedAt = 0;
  private long accumulatedTime = 0;

  public boolean isRunning() {
    return startedAt != 0;
  }

  public void start() {
    if (isRunning()) {
      throw new IllegalStateException("Staring stopwatch failed because it is already running.");
    }
    startedAt = System.currentTimeMillis();
  }

  public long pause() {
    if (!isRunning()) {
      throw new IllegalStateException(("Pausing stopwatch failed because it is not running."));
    }

    accumulatedTime = peek();
    startedAt = 0;
    return accumulatedTime;
  }

  public long peek() {
    if (!isRunning()) {
      return accumulatedTime;
    }

    long now = System.currentTimeMillis();
    return accumulatedTime + (now - startedAt);
  }

  public void reset() {
    startedAt = 0;
    accumulatedTime = 0;
  }

}
