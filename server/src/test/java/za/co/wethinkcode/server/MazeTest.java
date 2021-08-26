package za.co.wethinkcode.server;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {

  @Test
  void RandomMazeObstacleSizes() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    RandomMaze randomMaze = new RandomMaze(world);
    assertTrue(randomMaze.getSquareObstacles().size() < 9);
    assertTrue(randomMaze.getPitObstacles().size() < 9);
    assertTrue(randomMaze.getMineObstacles().size() < 9);
  }

  @Test
  void randomMazeObstaclesWithinAreaLimits() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    RandomMaze randomMaze = new RandomMaze(world);
    List<Obstacle> so = randomMaze.getSquareObstacles();
    List<Obstacle> po = randomMaze.getPitObstacles();
    List<Obstacle> mo = randomMaze.getMineObstacles();

    for (Obstacle square : so) {
      Position testPosition = new Position(square.getBottomLeftX(), square.getBottomLeftY());
      assertTrue(testPosition.isIn(new Position(-100, 200), new Position(100, -200)));
    }

    for (Obstacle pit : po) {
      Position testPosition = new Position(pit.getBottomLeftX(), pit.getBottomLeftY());
      assertTrue(testPosition.isIn(new Position(-100, 200), new Position(100, -200)));
    }

    for (Obstacle mine : mo) {
      Position testPosition = new Position(mine.getBottomLeftX(), mine.getBottomLeftY());
      assertTrue(testPosition.isIn(new Position(-100, 200), new Position(100, -200)));
    }
  }
}
