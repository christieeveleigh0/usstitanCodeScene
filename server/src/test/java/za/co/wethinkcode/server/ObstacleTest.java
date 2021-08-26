package za.co.wethinkcode.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {

  @Test
  void squareObstacleDimensions() {
    Obstacle obstacle = new SquareObstacle(1, 1);
    assertEquals(1, obstacle.getBottomLeftX());
    assertEquals(1, obstacle.getBottomLeftY());
    assertEquals(5, obstacle.getSize());
  }

  @Test
  void pitObstacleDimensions() {
    Obstacle obstacle = new Pit(1, 1);
    assertEquals(1, obstacle.getBottomLeftX());
    assertEquals(1, obstacle.getBottomLeftY());
    assertEquals(5, obstacle.getSize());
  }

  @Test
  void mineObstacleDimensions() {
    Obstacle obstacle = new Mine(1, 1);
    assertEquals(1, obstacle.getBottomLeftX());
    assertEquals(1, obstacle.getBottomLeftY());
    assertEquals(1, obstacle.getSize());
  }

  @Test
  void robotObstacleDimensions() {
    Obstacle obstacle = new RobotObstacle("hal", 1, 1);
    assertEquals(1, obstacle.getBottomLeftX());
    assertEquals(1, obstacle.getBottomLeftY());
    assertEquals(3, obstacle.getSize());
  }

  @Test
  void edgeObstacleDimensions() {
    Obstacle obstacle = new EdgeObstacle("right");
    assertEquals(100, obstacle.getBottomLeftX());
    assertEquals(-200, obstacle.getBottomLeftY());
    assertEquals(1, obstacle.getSize());
  }

  @Test
  void squareObstacleBlocksPosition() {
    Obstacle obstacle = new SquareObstacle(1, 1);
    assertTrue(obstacle.blocksPosition(new Position(1, 1)));
    assertTrue(obstacle.blocksPosition(new Position(5, 1)));
    assertTrue(obstacle.blocksPosition(new Position(1, 5)));
    assertFalse(obstacle.blocksPosition(new Position(0, 5)));
    assertFalse(obstacle.blocksPosition(new Position(6, 5)));
  }

  @Test
  void pitObstacleBlocksPosition() {
    Obstacle obstacle = new Pit(1, 1);
    assertTrue(obstacle.blocksPosition(new Position(1, 1)));
    assertTrue(obstacle.blocksPosition(new Position(5, 1)));
    assertTrue(obstacle.blocksPosition(new Position(1, 5)));
    assertFalse(obstacle.blocksPosition(new Position(0, 5)));
    assertFalse(obstacle.blocksPosition(new Position(6, 5)));
  }

  @Test
  void mineObstacleBlocksPosition() {
    Obstacle obstacle = new Mine(1, 1);
    assertTrue(obstacle.blocksPosition(new Position(1, 1)));
    assertFalse(obstacle.blocksPosition(new Position(0, 1)));
    assertFalse(obstacle.blocksPosition(new Position(1, 0)));
    assertFalse(obstacle.blocksPosition(new Position(1, 2)));
    assertFalse(obstacle.blocksPosition(new Position(2, 1)));
  }

  @Test
  void RobotObstacleBlocksPosition() {
    Obstacle obstacle = new RobotObstacle("hal", 1, 1);
    assertTrue(obstacle.blocksPosition(new Position(1, 1)));
    assertTrue(obstacle.blocksPosition(new Position(2, 1)));
    assertTrue(obstacle.blocksPosition(new Position(1, 2)));
    assertFalse(obstacle.blocksPosition(new Position(0, 5)));
    assertFalse(obstacle.blocksPosition(new Position(6, 5)));
  }

  @Test
  void edgeObstacleBlocksPosition() {
    Obstacle obstacle = new EdgeObstacle("right");
    assertTrue(obstacle.blocksPosition(new Position(100, -200)));
    assertFalse(obstacle.blocksPosition(new Position(200, -100)));
    assertFalse(obstacle.blocksPosition(new Position(-100, 199)));
    assertFalse(obstacle.blocksPosition(new Position(-100, 201)));
    assertFalse(obstacle.blocksPosition(new Position(-99, 200)));
  }

  @Test
  void squareObstacleBlockPath() {
    Obstacle obstacle = new SquareObstacle(1, 1);
    assertTrue(obstacle.blocksPath(new Position(1, 0), new Position(1, 50)));
    assertTrue(obstacle.blocksPath(new Position(2, -10), new Position(2, 100)));
    assertTrue(obstacle.blocksPath(new Position(-10, 5), new Position(20, 5)));
    assertFalse(obstacle.blocksPath(new Position(0, 1), new Position(0, 100)));
    assertFalse(obstacle.blocksPath(new Position(1, 6), new Position(1, 100)));
  }

  @Test
  void pitObstacleBlockPath() {
    Obstacle obstacle = new Pit(1, 1);
    assertTrue(obstacle.blocksPath(new Position(1, 0), new Position(1, 50)));
    assertTrue(obstacle.blocksPath(new Position(2, -10), new Position(2, 100)));
    assertTrue(obstacle.blocksPath(new Position(-10, 5), new Position(20, 5)));
    assertFalse(obstacle.blocksPath(new Position(0, 1), new Position(0, 100)));
    assertFalse(obstacle.blocksPath(new Position(1, 6), new Position(1, 100)));
  }

  @Test
  void mineObstacleBlockPath() {
    Obstacle obstacle = new Mine(1, 1);
    assertTrue(obstacle.blocksPath(new Position(1, 0), new Position(1, 50)));
    assertTrue(obstacle.blocksPath(new Position(-2, 1), new Position(2, 1)));
    assertTrue(obstacle.blocksPath(new Position(10, 1), new Position(0, 1)));
    assertFalse(obstacle.blocksPath(new Position(0, 1), new Position(0, 100)));
    assertFalse(obstacle.blocksPath(new Position(1, 6), new Position(1, 100)));
  }

  @Test
  void robotObstacleBlockPath() {
    Obstacle obstacle = new RobotObstacle("hal", 1, 1);
    assertTrue(obstacle.blocksPath(new Position(1, 0), new Position(1, 50)));
    assertTrue(obstacle.blocksPath(new Position(2, -10), new Position(2, 100)));
    assertTrue(obstacle.blocksPath(new Position(-10, 3), new Position(20, 3)));
    assertFalse(obstacle.blocksPath(new Position(0, 1), new Position(0, 100)));
    assertFalse(obstacle.blocksPath(new Position(1, 6), new Position(1, 100)));
  }

  @Test
  void edgeObstacleBlockPath() {
    Obstacle obstacle = new EdgeObstacle("bottom");
    assertTrue(obstacle.blocksPath(new Position(-99, -200), new Position(-100, -200)));
    assertTrue(obstacle.blocksPath(new Position(-100, -10), new Position(-100, -201)));
    assertTrue(obstacle.blocksPath(new Position(-100, 4), new Position(-100, -400)));
    assertFalse(obstacle.blocksPath(new Position(0, 1), new Position(0, 100)));
    assertFalse(obstacle.blocksPath(new Position(1, 6), new Position(1, 100)));
  }
}
