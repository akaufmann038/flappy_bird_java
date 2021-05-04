package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.game;
import model.tube;

public class gameView implements ActionListener {
  JFrame f; // Outer frame
  gamePanel gPanel; // inner game panel
  JButton jumpButton; // button to make bird jump
  int windowHeight = 600; // window height
  int windowWidth = 700; // window width
  game flappyGame; // model game object
  int timePassed; // keeps track of how much time has passed
  int timerDelay; // delay of timer object
  boolean isGameOver;
  Timer timer;

  // constructor
  public gameView(game flappyGame) {
    this.flappyGame = flappyGame; // model game object

    this.f = new JFrame();
    this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.jumpButton = new JButton("Jump"); // creates button
    this.jumpButton.setBounds(200, 500, 200, 50);
    this.jumpButton.addActionListener(this);
    this.f.add(this.jumpButton); // adds button to Frame

    this.gPanel = new gamePanel(); // creates game panel
    this.f.add(this.gPanel); // adds game panel to Frame

    this.isGameOver = true;

    this.timePassed = 0;
    this.timerDelay = 30;

    // timer object
    timer = new Timer(this.timerDelay, this.getTimerListener());

    this.f.pack();
    this.f.setSize(this.windowWidth, this.windowHeight);
    this.f.setLayout(null);
    this.f.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // if game is over, reset game state and start timer
    if (this.isGameOver) {
      this.isGameOver = false;
      this.resetGame();
      this.timer.start();
    }

    this.flappyGame.jumpBird();
    this.gPanel.repaint();
  }

  class gamePanel extends JPanel {
    int panelHeight;
    int panelWidth;
    Image izzy;

    // constructor
    public gamePanel() {
      setBorder(BorderFactory.createLineBorder(Color.black));
      this.panelHeight = 500;
      this.panelWidth = 700;
    }

    // sets size of game panel
    public Dimension getPreferredSize() {
      return new Dimension(this.panelWidth, this.panelHeight);
    }

    // paints shapes on panel
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      // paints tubes
      for (tube current : flappyGame.getTubes()) {
        g.setColor(Color.GREEN);
        // paints upper tube
        g.fillRect(current.getXLoc(), 0, current.getTubeWidth(), current.getUpperTubeLength());
        // paints lower tube
        g.fillRect(current.getXLoc(), panelHeight - current.getLowerTubeLength(),
                current.getTubeWidth(), current.getLowerTubeLength());
      }
      // draws score
      g.setColor(Color.BLACK);
      Font font = new Font("Serif", Font.PLAIN, 50);
      g.setFont(font);
      String score = flappyGame.getScore();
      g.drawString(score, this.panelWidth / 2, 100);

      // draws bird
      g.setColor(Color.GRAY);
      g.fillRect(flappyGame.getBirdX(), flappyGame.getBirdY(), flappyGame.getBirdSize(),
              flappyGame.getBirdSize());

      // draws the start directions
      if (isGameOver) {
        g.setColor(Color.BLACK);
        g.drawString("Press space to start game!", 100, 300);
      }
    }

    public int getPanelHeight() {
      return this.panelHeight;
    }
  }

  // creates ActionListener for timer object
  public ActionListener getTimerListener() {
    // ActionListener for timer object
    ActionListener timerListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        timePassed += 30;
        flappyGame.moveBird(); // moves the bird up and down
        // move tubes across the screen by decrementing x values
        flappyGame.moveTubes();

        // generates new tubes every 1200 milliseconds
        if (timePassed == 1200) {
          flappyGame.generateTubes(); // generates a new tube out of the screen
          timePassed = 0;
        }



        //TODO: check for ball hitting tube
        isGameOver = flappyGame.isHit(gPanel.getPanelHeight());
        if (isGameOver) {
          timer.stop();
        }

        // checks for score change and changes if requirements fit
        flappyGame.changeScore();

        gPanel.repaint(); // updates panel
      }
    };
    return timerListener;
  }

  // resets game state by creating new game state
  public void resetGame() {
    this.flappyGame = new game();
  }
}
