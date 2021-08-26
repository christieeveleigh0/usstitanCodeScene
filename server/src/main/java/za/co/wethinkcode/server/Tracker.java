package za.co.wethinkcode.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tracker.
 */
public class Tracker {
    private World.Direction myDirection;

    /**
     * Instantiates a new Tracker.
     *
     * @param myDirection the my direction
     */
    public Tracker(World.Direction myDirection) {
        myDirection = myDirection;
    }

    /**
     * Gets object map.
     *
     * @param type     the type
     * @param distance the distance
     * @return the object map
     */
    public Map<String, Object> getObjectMap(String type, int distance) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("direction", myDirection);
        objectMap.put("type", type);
        objectMap.put("distance", distance);
        return objectMap;
    }

    /**
     * Gets step distance.
     *
     * @param facing  the facing
     * @param robotX  the robot x
     * @param robotY  the robot y
     * @param objectX the object x
     * @param objectY the object y
     * @return the step distance
     */
    public int getStepDistance(World.Direction facing, int robotX, int robotY, int objectX, int objectY) {
        switch (facing) {
            case NORTH:
                return objectY - robotY;
            case EAST:
                return objectX - robotX;
            case SOUTH:
                return Math.abs(objectY + robotY);
            default:
                return Math.abs(objectX + robotX);
        }
    }

    /**
     * Track world . update response.
     *
     * @param obstacles     the obstacles
     * @param robotPosition the robot position
     * @param newPosition   the new position
     * @return the world . update response
     */
    public World.UpdateResponse track(List<Obstacle> obstacles, Position robotPosition, Position newPosition) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.blocksPath(robotPosition, newPosition)) {
                if (obstacle instanceof SquareObstacle) {
                    return World.UpdateResponse.FAILED_SQUARE;
                } else if (obstacle instanceof Pit) {
                    return World.UpdateResponse.FAILED_PIT;
                } else if (obstacle instanceof Mine) {
                    return World.UpdateResponse.FAILED_MINE;
                } else if (obstacle instanceof RobotObstacle) {
                    return World.UpdateResponse.FAILED_ROBOT;
                }
            }
        }
        return World.UpdateResponse.SUCCESS;
    }

    /**
     * Kill shot boolean.
     *
     * @param obstacles   the obstacles
     * @param world       the world
     * @param hunter      the hunter
     * @param newPosition the new position
     * @return the boolean
     */
    public boolean killShot(List<Obstacle> obstacles, World world, Robot hunter, Position newPosition) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.blocksPath(hunter.getPosition(), newPosition) && obstacle instanceof RobotObstacle) {
                hunter.addHit(obstacle.getName());
                String name = obstacle.getName();
                Robot prey = world.getRobot(name);
                world.executeDamage(prey, "firearm");
                return true;
            }
        }
        return false;
    }
}
