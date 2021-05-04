package model;

public class tube {
  int xLoc;
  int tubeSpace; // represents space in between tubes
  int tubeWidth;
  int upperTubeLength;
  int lowerTubeLength;

  // constructs a tube given the upperTubeHeight
  public tube(int upperTubeLength) {
    this.tubeSpace = 150; // space between tubes
    this.tubeWidth = 40; // width of tube
    this.xLoc = this.tubeWidth + 700; // spawning location of tube outside of the window
    this.upperTubeLength = upperTubeLength;
    this.lowerTubeLength = this.computeLowerLength(upperTubeLength);
  }

  // gets the length of the lower tube given the height of the upper tube
  public int computeLowerLength(int upperTubeLength) {
    return 500 - upperTubeLength - this.tubeSpace;
  }

  // moves the tube across the screen
  public void moveTube() {
    this.xLoc -= 10;
  }

  public int getXLoc() {
    return this.xLoc;
  }

  public int getUpperTubeLength() {
    return this.upperTubeLength;
  }

  public int getLowerTubeLength() {
    return this.lowerTubeLength;
  }

//  public int getTubeSpace() {
//    return this.tubeSpace;
//  }

  public int getTubeWidth() {
    return this.tubeWidth;
  }

  // TODO: delete method after done testing
  public void printTube() {
    System.out.println("Tube: " + this.xLoc);
  }
}
