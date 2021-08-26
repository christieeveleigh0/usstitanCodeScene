package za.co.wethinkcode.server;

/**
 * The type Edge obstacle.
 */
public class EdgeObstacle extends Obstacle {
  private int size;

  /**
   * Instantiates a new Edge obstacle.
   *
   * @param side the side
   */
  public EdgeObstacle(String side) {
    super("EDGE", 100, 200);
    switch (side) {
      case "top":
        setBottomLeftX(-100);
        break;
      case "right":
        setBottomLeftY(-200);
        break;
      default:
        setBottomLeftX(-100);
        setBottomLeftY(-200);
        break;
    }
    this.size = 1;
  }

  @Override
  public int getSize() {
    return size;
  }
}
