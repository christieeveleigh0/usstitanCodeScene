package za.co.wethinkcode.server;

import java.util.Objects;

/**
 * A location marker.
 */
public class Position {

  private final int x;
  private final int y;

  /**
   * Instantiates a new Position.
   *
   * @param x the x
   * @param y the y
   */
  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x-coordinate of the position.
   *
   * @return the x
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y-coordinate of the position.
   *
   * @return the y
   */
  public int getY() {
    return y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Position position = (Position) o;
    return x == position.x && y == position.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  /**
   * Checks if the x and y coordinates of this position fall within the corners of the world.
   *
   * @param topLeft     the upper left corner of the world
   * @param bottomRight the lower right corner of the world
   * @return the boolean
   */
  public boolean isIn(Position topLeft, Position bottomRight) {
    boolean withinTop = this.y <= topLeft.getY();
    boolean withinBottom = this.y >= bottomRight.getY();
    boolean withinLeft = this.x >= topLeft.getX();
    boolean withinRight = this.x <= bottomRight.getX();
    return withinTop && withinBottom && withinLeft && withinRight;
  }

  @Override
  public String toString() {
    return "[" + getX() + ", " + getY() + "]";
  }
}
