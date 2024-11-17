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

  public List<JButton> createButtons() {
    List<JButton> buttonList = new ArrayList<>();

    for (int i = 0; i < allBtns; i++) {
      if (i == allBtns - 1) {
        JButton button = new JButton();
        buttonList.add(button);
        button.addActionListener(this::gameMovement);
      } else {
        JButton button = new JButton(String.valueOf(i + 1));
        buttonList.add(button);
        button.addActionListener(this::gameMovement);

      }
    }
    return buttonList;
  }

  public JPanel createGameBoard() {
    JPanel gamePanel = new JPanel(new GridLayout(4, 4));
    List<JButton> buttonListCopy = new ArrayList<>(buttonList);
    if (buttonListCopy.size() > 1) {
      Collections.shuffle(buttonListCopy.subList(0, buttonListCopy.size() - 1));
    }

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
      if (Integer.parseInt(buttonText) < i - 1) {
        return;
      }
    }

    checkGameWon();
  }

  public void checkGameWon() {
    winText.setVisible(true);
  }

  



  public Gui() {

    this.setLayout(new BorderLayout());

    JPanel EastPanel = new JPanel();
    JPanel gamePanel = createGameBoard();
    EastPanel.setLayout(new BoxLayout(EastPanel, BoxLayout.Y_AXIS));
    newGameBtn.setAlignmentY(Component.CENTER_ALIGNMENT);

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


