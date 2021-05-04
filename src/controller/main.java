package controller;

import model.game;
import view.gameView;

public class main {
  public static void main(String[] args) {
    game newGame = new game();
    gameView view = new gameView(newGame);
  }
}
