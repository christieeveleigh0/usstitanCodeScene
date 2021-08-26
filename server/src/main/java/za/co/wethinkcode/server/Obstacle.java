package za.co.wethinkcode.server;

/**
 * The type Obstacle.
 */
public abstract class Obstacle {
  private int bottomLeftX;
  private int bottomLeftY;
  private String type;
  private String name;

  /**
   * Instantiates a new Obstacle.
   *
   * @param type        the type
   * @param bottomLeftX the bottom left x
   * @param bottomLeftY the bottom left y
   */
  public Obstacle(String type, int bottomLeftX, int bottomLeftY) {
    this.bottomLeftX = bottomLeftX;
    this.bottomLeftY = bottomLeftY;
    this.type = type;
    this.name = "";
  }

  /**
   * Instantiates a new Obstacle.
   *
   * @param type        the type
   * @param name        the name
   * @param bottomLeftX the bottom left x
   * @param bottomLeftY the bottom left y
   */
  public Obstacle(String type, String name, int bottomLeftX, int bottomLeftY) {
    this.bottomLeftX = bottomLeftX;
    this.bottomLeftY = bottomLeftY;
    this.type = type;
    this.name = name;
  }

  /**
   * Gets size.
   *
   * @return the size
   */
  public abstract int getSize();

  /**
   * Gets bottom left x.
   *
   * @return the bottom left x
   */
  public int getBottomLeftX() {
    return bottomLeftX;
  }

  /**
   * Sets bottom left x.
   *
   * @param x the x
   */
  public void setBottomLeftX(int x) {
    bottomLeftX = x;
  }

  /**
   * Gets bottom left y.
   *
   * @return the bottom left y
   */
  public int getBottomLeftY() {
    return bottomLeftY;
  }

  /**
   * Sets bottom left y.
   *
   * @param y the y
   */
  public void setBottomLeftY(int y) {
    bottomLeftY = y;
  }

  /**
   * Gets type.
   *
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Blocks position boolean.
   *
   * @param position the position
   * @return the boolean
   */
  public boolean blocksPosition(Position position) {
    int x = position.getX();
    int y = position.getY();
    return ((x >= getBottomLeftX() && x <= getBottomLeftX() + (getSize() - 1))
        && (y >= getBottomLeftY() && y <= getBottomLeftY() + (getSize() - 1)));
  }

  /**
   * Blocks path boolean.
   *
   * @param a the a
   * @param b the b
   * @return the boolean
   */
  public boolean blocksPath(Position a, Position b) {
    int x1 = a.getX();
    int y1 = a.getY();
    int x2 = b.getX();
    int y2 = b.getY();

    if (x1 == x2 && x1 >= getBottomLeftX() && x1 <= getBottomLeftX() + (getSize() - 1)) {
      if (((y1 > getBottomLeftY() + (getSize() - 1)) && (y2 <= getBottomLeftY() + (getSize() - 1)))
          || (y1 < getBottomLeftY() && y2 >= getBottomLeftY())) {
        return true;
      }
    } else if (y1 == y2 && y1 >= getBottomLeftY() && y1 <= getBottomLeftY() + (getSize()) - 1) {
      return (((x1 > getBottomLeftX() + (getSize() - 1))
              && (x2 <= getBottomLeftX() + (getSize() - 1)))
          || (x1 < getBottomLeftX() && x2 >= getBottomLeftX()));
    }
    return false;
  }

  @Override
  public String toString() {
    return "Obstacle{" +
            "\nbottomLeftX=" + bottomLeftX +
            "\nbottomLeftY=" + bottomLeftY +
            "\ntype='" + type +
            '}';
  }
}
