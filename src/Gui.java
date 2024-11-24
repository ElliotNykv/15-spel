import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Gui extends JFrame {
  List<JButton> buttonList = createButtons();
  private static final int gridSize = 4;
  private static final int allBtns = gridSize * gridSize;

  JButton newGameBtn = new JButton("Nytt spel");
  JLabel winText = new JLabel("Grattis, du vann!");
  JPanel gamePanel;

  public List<JButton> createButtons() {
    List<JButton> buttonList = new ArrayList<>();

    for (int i = 0; i < allBtns; i++) {
      JButton button;
      if (i == allBtns - 1) {
        button = new JButton();
        button.setText("");
      } else {
        button = new JButton(String.valueOf(i + 1));
      }
      button.addActionListener(this::gameMovement);
      buttonList.add(button);
    }
    return buttonList;
  }

  public JPanel createGameBoard() {
    gamePanel = new JPanel(new GridLayout(gridSize, gridSize));
    startOrder();

    for (JButton button : buttonList) {
      button.setPreferredSize(new Dimension(100, 100));
      gamePanel.add(button);
    }
    return gamePanel;
  }

  private void gameMovement(ActionEvent e) {
    JButton clickedButton = (JButton) e.getSource();
    int clickedIndex = buttonList.indexOf(clickedButton);
    int emptyIndex = buttonList.size() - 1;

    for (int i = 0; i < buttonList.size(); i++) {
      if (buttonList.get(i).getText().isEmpty()) {
        emptyIndex = i;
        break;
      }
    }

    if ((clickedIndex == emptyIndex - 1 && emptyIndex % gridSize != 0) ||
            (clickedIndex == emptyIndex + 1 && clickedIndex % gridSize != 0) ||
            (clickedIndex == emptyIndex - gridSize) || (clickedIndex == emptyIndex + gridSize)) {
      buttonList.get(emptyIndex).setText(clickedButton.getText());
      clickedButton.setText("");
      checkGameOrder();
    }
  }

  private void checkGameOrder() {
    for (int i = 0; i < buttonList.size() - 1; i++) {
      String buttonText = buttonList.get(i).getText();
      if (buttonText.isEmpty() || Integer.parseInt(buttonText) != i + 1) {
        return;
      }
    }
    if (buttonList.getLast().getText().isEmpty()) {
      checkGameWon(true);
    }
  }

  public void checkGameWon(boolean value) {
    winText.setVisible(value);
  }

  private void shuffleBoard() {
    if (buttonList.size() > 1) {

      setWinningOrder();

      List<Integer> numbers = new ArrayList<>();
      for (int i = 1; i < allBtns; i++) {
        numbers.add(Integer.valueOf(i));
      }

      Collections.shuffle(numbers);


      for (int i = 0; i < buttonList.size() - 1; i++) {
        buttonList.get(i).setText(String.valueOf(numbers.get(i)));
      }
      buttonList.getLast().setText("");
    }
  }

  private void setWinningOrder() {
    for (int i = 0; i < buttonList.size() - 1; i++) {
      buttonList.get(i).setText(String.valueOf(i + 1));
    }
    buttonList.getLast().setText("");
  }



  private void startOrder() {
    for (int i = 0; i < buttonList.size() - 1; i++) {
      buttonList.get(i).setText(String.valueOf(i + 1));
    }

    buttonList.get(buttonList.size() - 6).setText("");
    buttonList.get(buttonList.size() - 2).setText("11");
    buttonList.getLast().setText("15");
  }

  private void newGame(ActionEvent e) {
    checkGameWon(false);
    shuffleBoard();

    gamePanel.removeAll(); // Remove all components from the panel
    for (JButton button : buttonList) {
      gamePanel.add(button);
    }

    this.add(gamePanel, BorderLayout.CENTER);
    this.revalidate();
    this.repaint();
  }

  public Gui() {
    this.setLayout(new BorderLayout());

    startOrder();
    gamePanel = createGameBoard();

    JPanel EastPanel = new JPanel();
    EastPanel.setLayout(new BoxLayout(EastPanel, BoxLayout.Y_AXIS));
    newGameBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
    newGameBtn.addActionListener(this::newGame);

    EastPanel.add(winText);
    winText.setVisible(false);
    EastPanel.add(newGameBtn);

    this.add(EastPanel, BorderLayout.EAST);
    this.add(gamePanel, BorderLayout.CENTER);

    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
}


