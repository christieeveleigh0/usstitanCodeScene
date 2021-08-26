package za.co.wethinkcode.server;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    World world = new World(200, 400, 10, 7, 5, 9, 3);

    RequestObject requestObject = new RequestObject("{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Object launchCommand = Command.create(requestObject).execute(world);
    Robot robot = world.getRobot("hal");

    @Test
  void initValues() {
    assertEquals(new Position(-200 / 2, 400 / 2), world.getTopLeft());
    assertEquals(new Position(200 / 2, -400 / 2), world.getBottomRight());
    assertEquals(10, world.getVisibility());
    assertEquals(7, world.getRepairTime());
    assertEquals(5, world.getReloadTime());
    assertEquals(9, world.getMineSet());
    assertEquals(3, world.getShieldStrength());
    assertEquals(10, world.getShots());
    assertEquals(World.Direction.NORTH, world.getStartDirection());
  }

  @Test
  void updateDirectionRight() {
    world.updateDirection(robot, true);
    assertEquals(World.Direction.EAST, robot.getCurrentDirection());
  }

  @Test
  void updateDirectionLeft() {
    world.updateDirection(robot, false);
    assertEquals(World.Direction.WEST, robot.getCurrentDirection());
  }

  @Test
  void isNewPositionAllowed() {
    world.setMaze(new OpenFieldMaze(world));
    assertTrue(world.isNewPositionAllowed(new Position(100, 0)));
    assertTrue(world.isNewPositionAllowed(new Position(100, 100)));
    assertFalse(world.isNewPositionAllowed(new Position(201, 0)));
    assertFalse(world.isNewPositionAllowed(new Position(-201, 0)));
  }

  @Test
  void testFound() {
    List<Obstacle> obstacleList = Collections.singletonList(new SquareObstacle(1, 2));
    assertTrue(world.found(obstacleList, new Position(9, 10)));
    assertFalse(world.found(obstacleList, new Position(1, 2)));
  }
}
