package bakeit.stopwatch.ui;

import static java.lang.String.format;

import bakeit.stopwatch.domain.StopWatch;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.concurrent.TimeUnit;
import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.Timer;

public class StopWatchFrame extends JFrame {

  private JLabel elapsedTimeLabel;
  private JPanel controlPanel;
  private JPanel splitTimePanel;

  private JButton controlButton;
  private JButton resetButton;
  private JButton splitTimeButton;

  private DefaultListModel<Long> splitTimeModel;
  private JList<Long> splitTimeList;

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

  private ActionListener splitTimeListener = event -> {
    splitTimeClicked();
  };


  public StopWatchFrame() {
    super("Stop Watch");
    setSize(400, 400);
    setLayout(new GridBagLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    prepareGUI();
  }

  private void prepareGUI() {
    elapsedTimeLabel = new JLabel("00:00:00.000", JLabel.CENTER);

    elapsedTimeLabel.setFont(elapsedTimeLabel.getFont().deriveFont(24f));
    elapsedTimeLabel.setSize(350, 100);

    controlPanel = new JPanel();
    FlowLayout layout = new FlowLayout();
    layout.setHgap(10);
    layout.setVgap(10);
    controlPanel.setLayout(layout);

    controlButton = new JButton("Start");
    controlButton.addActionListener(startListener);
    controlPanel.add(controlButton);

    splitTimeButton = new JButton("Split Time");
    splitTimeButton.setEnabled(false);
    splitTimeButton.addActionListener(splitTimeListener);
    controlPanel.add(splitTimeButton);

    resetButton = new JButton("Reset");
    resetButton.addActionListener(resetListener);
    resetButton.setEnabled(false);
    controlPanel.add(resetButton);

    GridBagConstraints gbc = new GridBagConstraints();

    gbc.gridx = 0;
    gbc.gridy = 0;
    add(elapsedTimeLabel, gbc);

    gbc.gridy = 1;
    add(controlPanel, gbc);

    splitTimePanel = new JPanel();
    splitTimeModel = new DefaultListModel<>();
    splitTimeList = new JList<>(splitTimeModel);
    splitTimeList.setCellRenderer(new SplitTimeRenderer());
    splitTimePanel.add(new JScrollPane(splitTimeList));

    gbc.gridy = 2;
    add(splitTimePanel, gbc);
  }

  private void startClicked() {
    removeListeners(controlButton);
    controlButton.addActionListener(stopListener);
    controlButton.setText("Stop");
    splitTimeButton.setEnabled(true);

    stopWatch.start();
    timer.start();
  }

  private void stopClicked() {
    removeListeners(controlButton);
    controlButton.addActionListener(continueListener);
    controlButton.setText("Continue");
    resetButton.setEnabled(true);
    splitTimeButton.setEnabled(false);

    stopWatch.pause();
    timer.stop();
    updateTime();
  }

  private void continueClicked() {
    removeListeners(controlButton);
    controlButton.addActionListener(stopListener);
    controlButton.setText("Stop");
    resetButton.setEnabled(false);
    splitTimeButton.setEnabled(true);

    stopWatch.start();
    timer.start();
  }

  private void resetClicked() {
    removeListeners(controlButton);
    controlButton.addActionListener(startListener);
    controlButton.setText("Start");
    resetButton.setEnabled(false);
    splitTimeModel.removeAllElements();

    stopWatch.reset();
    updateTime();
  }

  private void splitTimeClicked() {
    long elapsedTime = stopWatch.peek();
    splitTimeModel.insertElementAt(elapsedTime, 0);
  }

  private void updateTime() {
    elapsedTimeLabel.setText(formatTime(stopWatch.peek()));
  }

  private String formatTime(long elapsedTime) {

    long hours = TimeUnit.MILLISECONDS.toHours(elapsedTime);
    long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime) % 60;
    long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60;
    long millis = elapsedTime % 1000;

    return format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
  }

  private void removeListeners(AbstractButton button) {
    for (ActionListener listener : button.getActionListeners()) {
      controlButton.removeActionListener(listener);
    }
  }

  public class SplitTimeRenderer extends JLabel implements ListCellRenderer<Long> {

    @Override
    public Component getListCellRendererComponent(JList<? extends Long> list, Long splitTime, int index,
        boolean isSelected, boolean cellHasFocus) {

      setText(formatTime(splitTime));

      return this;
    }

  }
}