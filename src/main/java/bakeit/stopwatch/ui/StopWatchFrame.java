package bakeit.stopwatch.ui;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class StopWatchFrame extends JFrame {

  private JLabel elapsedTimeLabel;
  private JPanel controlPanel;

  private JButton controlButton;
  private JButton resetButton;

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
  }

  private void stopClicked() {
    removeListeners(controlButton);
    controlButton.addActionListener(continueListener);
    controlButton.setText("Continue");
    resetButton.setEnabled(true);
  }

  private void continueClicked() {
    removeListeners(controlButton);
    controlButton.addActionListener(stopListener);
    controlButton.setText("Stop");
    resetButton.setEnabled(false);
  }

  private void resetClicked() {
    removeListeners(controlButton);
    controlButton.addActionListener(startListener);
    controlButton.setText("Start");
    resetButton.setEnabled(false);
  }

  private void removeListeners(AbstractButton button) {
    for (ActionListener listener : button.getActionListeners()) {
      controlButton.removeActionListener(listener);
    }
  }
}