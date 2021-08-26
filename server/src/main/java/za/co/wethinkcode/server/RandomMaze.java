package za.co.wethinkcode.server;

import java.util.Random;

/**
 * The type Random maze.
 */
public class RandomMaze extends Maze {
  /**
   * The Random.
   */
  Random random = new Random();

  /**
   * Instantiates a new Random maze.
   *
   * @param world the world
   */
  public RandomMaze(World world) {
    super(world);
    this.generateObstacles();
  }

  @Override
  public void generateObstacles() {
    int squares = random.nextInt(8);
    int pits = random.nextInt(8);
    int mines = random.nextInt(8);

    for (int i = 0; i <= squares; i++) {
      int squareX = random.nextInt(100);
      int squareY = random.nextInt(200);
      Obstacle so = new SquareObstacle(squareX, squareY);
      addSquareObstacle(so);
    }

    for (int i = 0; i <= pits; i++) {
      int pitX = random.nextInt(100);
      int pitY = random.nextInt(200);
      Obstacle po = new Pit(pitX, pitY);
      addPitObstacle(po);
    }

    for (int i = 0; i <= mines; i++) {
      int mineX = random.nextInt(100);
      int mineY = random.nextInt(200);
      Obstacle mo = new Mine(mineX, mineY);
      addMineObstacle(mo);
    }
  }
}
