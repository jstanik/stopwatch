package bakeit.stopwatch.domain;

import java.util.ArrayList;
import java.util.List;

public class StopWatch {

  private long startedAt = 0;
  private long accumulatedTime = 0;

  private List<Long> splitTimes = new ArrayList<>();

  public boolean isRunning() {
    return startedAt != 0;
  }

  public void start() {
    if (isRunning()) {
      throw new IllegalStateException("Starting stopwatch failed because it si already running.");
    }

    startedAt = System.currentTimeMillis();
  }

  public long pause() {
    if (!isRunning()) {
      throw new IllegalStateException("Pausing stopwatch failed because it is not running.");
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
    splitTimes.clear();
  }

  /**
   * Records new split time.
   *
   * @return the latest recorded split time.
   */
  public long splitTime() {
    if (! isRunning()) {
      throw new IllegalStateException("StopWatch not running.");
    }

    long splitTime = peek();
    splitTimes.add(splitTime);
    return splitTime;
  }

  /**
   * Gets all the recorded split times since last reset.
   *
   * @return a new list of split times.
   */
  public List<Long> getSplitTimes() {
    return List.copyOf(splitTimes);
  }

}
