package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class game {
  int birdX; // x location of bird
  int birdY; // y location of bird
  int birdSize;
  int gravity; // rate of change for change in y
  int changeY; // change in y for bird every game tick
  ArrayList<tube> gameTubes; // list of tubes
  int score; // represents the score of the game
  int currentTube; // represents the index value in gameTubes of the next tube the bird will enter

  public game() {
    //250
    this.birdX = 250;
    this.birdY = 100;
    this.birdSize = 30;
    this.gravity = 2;
    this.changeY = 10;
    this.gameTubes = new ArrayList<>();
    this.score = 0;
    this.currentTube = 0;
  }

  // moves the bird down (simulating gravity)
  public void moveBird() {
    this.changeY += this.gravity;
    this.birdY += this.changeY;
  }

  // makes bird jump by changing rate of change to negative number
  public void jumpBird() {
    this.changeY = -20;
  }

  public int getBirdX() {
    return this.birdX;
  }

  public int getBirdY() {
    return this.birdY;
  }

  public ArrayList<tube> getTubes() {
    return this.gameTubes;
  }

  public String getScore() {
    return "" + this.score;
  }

  public int getBirdSize() {
    return this.birdSize;
  }

  // adds a tube to gameTubes with the length generating randomly within a certain range
  public void generateTubes() {
    // generates random number
    Random r = new Random();
    int randLength = 50 + r.nextInt(250);
    tube newTube = new tube(randLength);
    this.gameTubes.add(newTube);
  }

  // decreases the x values of each tube in gameTubes,
  // causing them to move across the screen
  public void moveTubes() {
    for (tube current : this.gameTubes) {
      current.moveTube();
    }
  }

  //TODO: checks if bird hit current tube or if bird hit the ceiling or floor of the game
  public boolean isHit(int panelHeight) {
    boolean hit = false;

    if (this.gameTubes.size() > 0) {
      tube currentTube = this.gameTubes.get(this.currentTube);
      // test if bird is within tube
      if (this.birdX + this.birdSize >= currentTube.getXLoc()) {
        // test if bird hits top or bottom of tube
        if ((this.birdY <= currentTube.getUpperTubeLength()) ||
                (this.birdY + this.birdSize >= panelHeight - currentTube.getLowerTubeLength())) {
          hit = true;
        }
      }
    }

    // test if bird hits ceiling or floor
    if (this.birdY < 0 || (this.birdY + this.birdSize) >= panelHeight) {
      hit = true;
    }

    return hit;
  }

  // increments the game score and increments currentTube if the right side of the current tube is
  // less than the x location value of the bird
  public void changeScore() {
    // gets current tube
    if (this.gameTubes.size() > 0) {
      tube currentT = this.gameTubes.get(this.currentTube);
      if (currentT.getXLoc() + currentT.getTubeWidth() < this.birdX) {
        this.score += 1;
        this.currentTube += 1;
      }
    }
  }

  // TODO: delete after
  public String toString() {
    return "The height of the bird is: " + this.birdY;
  }

  // TODO: delete method after done testing
  public void printTubes() {
    if (this.gameTubes.size() > 0) {
      for (int i = 0; i < 1; i++) {
        this.gameTubes.get(i).printTube();
      }
    }
  }
}