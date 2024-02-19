package bakeit.stopwatch.ui;

import static java.lang.String.format;

import bakeit.stopwatch.domain.StopWatch;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.concurrent.TimeUnit;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class StopWatchFrame extends JFrame {

  private JLabel elapsedTimeLabel;
  private JPanel controlPanel;

  private JButton controlButton;
  private JButton resetButton;

  private StopWatch stopWatch = new StopWatch();

  private Timer timer = new Timer(5, event -> updateTime());

  private ActionListener startListener = event -> {
    startClicked();
  };

  private ActionListener stopListener = event -> {
    stopClicked();
  };

  private ActionListener continueListener = event -> {
    continueClicked();
  };


  private ActionListener resetListener = event -> {
    resetClicked();
  };

  public StopWatchFrame() {
    super("Stop Watch");
    setSize(300, 200);
    setLayout(new GridBagLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    prepareGUI();
  }

  private void prepareGUI() {
    elapsedTimeLabel = new JLabel("00:00:00.000", JLabel.CENTER);

    elapsedTimeLabel.setFont(elapsedTimeLabel.getFont().deriveFont(18f));
    elapsedTimeLabel.setSize(350, 100);

    controlPanel = new JPanel();
    FlowLayout layout = new FlowLayout();
    layout.setHgap(10);
    layout.setVgap(10);
    controlPanel.setLayout(layout);

    resetButton = new JButton("Reset");
    resetButton.addActionListener(resetListener);
    resetButton.setEnabled(false);
    controlPanel.add(resetButton);

    controlButton = new JButton("Start");
    controlButton.addActionListener(startListener);
    controlPanel.add(controlButton);

    GridBagConstraints gbc = new GridBagConstraints();

    gbc.gridx = 0;
    gbc.gridy = 0;
    add(elapsedTimeLabel, gbc);

    gbc.gridy = 1;
    add(controlPanel, gbc);
  }

  private void startClicked() {
    removeListeners(controlButton);
    controlButton.addActionListener(stopListener);
    controlButton.setText("Stop");

    stopWatch.start();
    timer.start();
  }

  private void stopClicked() {
    removeListeners(controlButton);
    controlButton.addActionListener(continueListener);
    controlButton.setText("Continue");
    resetButton.setEnabled(true);

    stopWatch.pause();
    timer.stop();
    updateTime();
  }

  private void continueClicked() {
    removeListeners(controlButton);
    controlButton.addActionListener(stopListener);
    controlButton.setText("Stop");
    resetButton.setEnabled(false);

    stopWatch.start();
    timer.start();
  }

  private void resetClicked() {
    removeListeners(controlButton);
    controlButton.addActionListener(startListener);
    controlButton.setText("Start");
    resetButton.setEnabled(false);

    stopWatch.reset();
    updateTime();
  }

  private void updateTime() {
    long millis = stopWatch.peek();

    long hours = TimeUnit.MILLISECONDS.toHours(millis);
    long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
    long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
    long remainingMillis = millis % 1000;

    String timeToDisplay = format("%02d:%02d:%02d.%03d", hours, minutes, seconds, remainingMillis);
    elapsedTimeLabel.setText(timeToDisplay);
  }

  private void removeListeners(AbstractButton button) {
    for (ActionListener listener : button.getActionListeners()) {
      controlButton.removeActionListener(listener);
    }
  }
}