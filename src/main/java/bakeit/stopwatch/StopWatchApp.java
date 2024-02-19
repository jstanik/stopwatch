package bakeit.stopwatch;

import bakeit.stopwatch.ui.StopWatchFrame;
import javax.swing.SwingUtilities;

public class StopWatchApp {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      StopWatchFrame stopWatchFrame = new StopWatchFrame();
      stopWatchFrame.setVisible(true);
    });
  }

}
