package za.co.wethinkcode.server;

import com.google.common.util.concurrent.Uninterruptibles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A Robot.
 */
public class Robot {
  private String make;
  private String name;
  private Map<String, Object> data;
  private Map<String, Object> state;
  private Position position;
  private World.Direction currentDirection;
  private Integer shields;
  private Integer shots;
  private World.Mode status;
  private int shieldMax;
  private int shotsMax;
  private int repairTime;
  private int reloadTime;
  private int mineTime;
  private List<String> hitList;
  private List<Map<String, Object>> lookerList;

  /**
   * Instantiates a new Robot.
   *
   * @param make  the make
   * @param name  the name
   * @param data  the data
   * @param state the state
   */
  public Robot(String make, String name, Map<String, Object> data, Map<String, Object> state) {
    this.make = make;
    this.name = name;
    this.data = data;
    this.state = state;
    this.currentDirection = World.Direction.NORTH;
    this.status = World.Mode.NORMAL;
    this.shields = (Integer) this.state.get("shields");
    this.shots = (Integer) this.state.get("shots");
    this.shotsMax = 0;
    this.shieldMax = 0;
    this.repairTime = 0;
    this.reloadTime = 0;
    this.mineTime = 0;
    this.hitList = new ArrayList<>();
    this.lookerList = new ArrayList<>();
  }

  /**
   * Gets make.
   *
   * @return the make
   */
  public String getMake() {
    return this.make;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets data.
   *
   * @return the data
   */
  public Map<String, Object> getData() {
    return this.data;
  }

  /**
   * Gets state.
   *
   * @return the state
   */
  public Map<String, Object> getState() {
    updateState();
    return this.state;
  }

  /**
   * Update state.
   */
  public void updateState() {
    List<Integer> coordinates = new ArrayList<>();
    coordinates.add(getPosition().getX());
    coordinates.add(getPosition().getY());
    this.state.put("position", coordinates);
    this.state.put("direction", getCurrentDirection());
    this.state.put("shields", getShields());
    this.state.put("shots", getShots());
    this.state.put("status", getStatus());
  }

  /**
   * Gets position.
   *
   * @return the position
   */
  public Position getPosition() {
    return this.position;
  }

  /**
   * Sets position.
   *
   * @param position the position
   */
  public void setPosition(Position position) {
    this.position = position;
  }

  /**
   * Gets current direction.
   *
   * @return the current direction
   */
  public World.Direction getCurrentDirection() {
    return this.currentDirection;
  }

  /**
   * Sets current direction.
   *
   * @param currentDirection the current direction
   */
  public void setCurrentDirection(World.Direction currentDirection) {
    this.currentDirection = currentDirection;
  }

  /**
   * Gets shields.
   *
   * @return the shields
   */
  public Integer getShields() {
    return shields;
  }

  /**
   * Sets shields.
   *
   * @param shields the shields
   */
  public void setShields(Integer shields) {
    this.shields = shields;
  }

  /**
   * Gets shots.
   *
   * @return the shots
   */
  public Integer getShots() {
    return shots;
  }

  /**
   * Sets shots.
   *
   * @param shots the shots
   */
  public void setShots(Integer shots) {
    this.shots = shots;
  }

  /**
   * Minus shots.
   */
  public void minusShots() {
    if (shots > 0) {
      shots--;
    }
  }

  /**
   * Gets status.
   *
   * @return the status
   */
  public World.Mode getStatus() {
    return status;
  }

  /**
   * Sets status.
   *
   * @param status the status
   */
  public void setStatus(World.Mode status) {
    this.status = status;
  }

  /**
   * Sets shield max.
   *
   * @param strength the strength
   */
  public void setShieldMax(int strength) {
    shieldMax = strength;
  }

  /**
   * Gets shots max.
   *
   * @return the shots max
   */
  public int getShotsMax() {
    return shotsMax;
  }

  /**
   * Sets shots max.
   *
   * @param bullets the bullets
   */
  public void setShotsMax(int bullets) {
    shotsMax = bullets;
  }

  /**
   * Sets repair time.
   *
   * @param repairTime the repair time
   */
  public void setRepairTime(int repairTime) {
    this.repairTime = repairTime;
  }

  /**
   * Sets reload time.
   *
   * @param reloadTime the reload time
   */
  public void setReloadTime(int reloadTime) {
    this.reloadTime = reloadTime;
  }

  /**
   * Sets mine time.
   *
   * @param mineTime the mine time
   */
  public void setMineTime(int mineTime) {
    this.mineTime = mineTime;
  }

  /**
   * Repair.
   */
  public void repair() {
    setStatus(World.Mode.REPAIR);
    Uninterruptibles.sleepUninterruptibly(repairTime, TimeUnit.SECONDS);
    setShields(shieldMax);
  }

  /**
   * Reload.
   */
  public void reload() {
    setStatus(World.Mode.RELOAD);
    Uninterruptibles.sleepUninterruptibly(reloadTime, TimeUnit.SECONDS);
    setShots(shotsMax);
  }

  /**
   * Sets mine.
   *
   * @return the mine
   */
  public Mine setMine() {
    setStatus(World.Mode.SETMINE);
    Uninterruptibles.sleepUninterruptibly(mineTime, TimeUnit.SECONDS);
    return new Mine(getPosition().getX(), getPosition().getY());
  }

  /**
   * Gets hit list.
   *
   * @return the hit list
   */
  public List<String> getHitList() {
    return hitList;
  }

  /**
   * Add hit.
   *
   * @param hit the hit
   */
  public void addHit(String hit) {
    hitList.add(hit);
  }

  /**
   * Gets looker list.
   *
   * @return the looker list
   */
  public List<Map<String, Object>> getLookerList() {
    return lookerList;
  }

  /**
   * Clear looker list.
   */
  public void clearLookerList() {
    lookerList.clear();
  }

  /**
   * Add seen.
   *
   * @param seen the seen
   */
  public void addSeen(List<Map<String, Object>> seen) {
    lookerList.addAll(seen);
  }

  /**
   * Handle command object.
   *
   * @param command the command
   * @return the object
   */
  public Object handleCommand(Command command) {
    Object value = command.execute(this);
    setStatus(World.Mode.NORMAL);
    return value;
  }


  @Override
  public String toString() {
    return "Robot{" +
            "make='" + make + '\'' +
            ", name='" + name + '\'' +
            ", hitList=" + hitList +
            '}';
  }
}
