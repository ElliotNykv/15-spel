import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Gui extends JFrame {
  List <JButton> buttonList = createButtons();
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
    shuffleBoard();

    for (JButton button : buttonList) {
      button.setPreferredSize(new Dimension(100, 100));
      gamePanel.add(button);
    }
    return gamePanel;
  }


  private void gameMovement(ActionEvent e) {
    JButton clickedButton = (JButton) e.getSource();
    int clickedIndex = buttonList.indexOf(clickedButton);
    int emptyIndex = buttonList.size();

    for (int i = 0; i < buttonList.size(); i++) {
      if (buttonList.get(i).getText().isEmpty()) {
        emptyIndex = i;
        break;
      }
    }

    if ((clickedIndex == emptyIndex - 1) || (clickedIndex == emptyIndex + 1) ||
            (clickedIndex == emptyIndex - gridSize) || (clickedIndex == emptyIndex + gridSize)) {
      buttonList.get(emptyIndex).setText(clickedButton.getText());
      clickedButton.setText("");
      checkGameOrder();
    }
  }

  private void checkGameOrder() {
    winText.setVisible(false);
    for (int i = 0; i < buttonList.size() - 1; i++) {
      String buttonText = buttonList.get(i).getText();
      if (buttonText.isEmpty()) {
        continue;
      }
      if (Integer.parseInt(buttonText) != i + 1) {
        return;
      }
    }

    checkGameWon(true);
  }

  public void checkGameWon(boolean value) {
    winText.setVisible(value);
  }

  private void shuffleBoard() {
    if (buttonList.size() > 1) {
      List<JButton> nonEmptyButtons = buttonList.subList(0, buttonList.size() - 1);
      Collections.shuffle(nonEmptyButtons);

      buttonList.getLast().setText("");
    }
  }



  private void newGame(ActionEvent e) {
    checkGameWon(false);
    this.remove(gamePanel);


    JPanel gamePanel = createGameBoard();
    this.add(gamePanel, BorderLayout.CENTER);

    this.revalidate();
    this.repaint();
  }





  public Gui() {

    this.setLayout(new BorderLayout());
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

    // Make sure this isn't resetting the layout
    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true); // Should be called after all components are added
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
}


