package za.co.wethinkcode.client;

import java.util.Random;

/**
 * The type Robot.
 */
public class Robot {
  private int visibility;
  private int reload;
  private int repair;
  private int mine;
  private int shieldHits;
  private boolean launched;
  private String output;
  private final String make;
  private final String name;
  private final int shield;
  private final int ammo;

  /**
   * A Robot.
   *
   * @param make   the make.
   * @param name   the name.
   * @param shield the amount of shields.
   * @param ammo   the amount of ammo.
   */
  public Robot(String make, String name, int shield, int ammo) {
    this.make = make;
    this.name = name;
    this.shield = shield;
    this.ammo = ammo;
    this.output = "ready";
  }

  /**
   * Gets the make of this Robot.
   *
   * @return the make
   */
  public String getMake() {
    return this.make;
  }

  /**
   * Gets name of this Robot.
   *
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the launched states of the robot.
   *
   * @param launched TRUE if the robot has launched, FALSE if not.
   */
  public void setLaunched(boolean launched) {
    this.launched = launched;
  }

  /**
   * Returns the launched status of the robot.
   *
   * @return the status.
   */
  public Boolean getLaunched() {
    return this.launched;
  }

  /**
   * Gets the number of shields for this Robot.
   *
   * @return the shields.
   */
  public int getShield() {
    return this.shield;
  }

  /**
   * Gets the amount of ammo this Robot can carry.
   *
   * @return the ammo amount.
   */
  public int getAmmo() {
    return this.ammo;
  }

  /**
   * Gets the command output.
   *
   * @return the output
   */
  public String getOutput() {
    return this.output;
  }

  /**
   * Sets the output.
   *
   * @param output the output
   */
  public void setOutput(String output) {
    this.output = output;
  }

  /**
   * Returns the visibility distance.
   *
   * @return the distance.
   */
  public int getVisibility() {
    return this.visibility;
  }

  /**
   * Sets the visibility distance.
   *
   * @param visibility the distance.
   */
  public void setVisibility(int visibility) {
    this.visibility = visibility;
  }

  /**
   * Returns the reload time.
   *
   * @return the time in seconds.
   */
  public int getReload() {
    return this.reload;
  }

  /**
   * Sets the reload time.
   *
   * @param reload the time in seconds.
   */
  public void setReload(int reload) {
    this.reload = reload;
  }

  /**
   * Returns the repair time.
   *
   * @return the time in seconds.
   */
  public int getRepair() {
    return this.repair;
  }

  /**
   * Sets the repair time.
   *
   * @param repair the time in seconds.
   */
  public void setRepair(int repair) {
    this.repair = repair;
  }

  /**
   * Returns the time required to place a mine.
   *
   * @return the time required in seconds.
   */
  public int getMine() {
    return this.mine;
  }

  /**
   * Sets the time required to place a mine.
   *
   * @param mine the time required in seconds.
   */
  public void setMine(int mine) {
    this.mine = mine;
  }

  /**
   * Returns the amount of hits a shield can take before it is broken.
   *
   * @return the amount of hits.
   */
  public int getShieldHits() {
    return this.shieldHits;
  }

  /**
   * Sets the amount of hits a shield can take before it is broken.
   *
   * @param hits the amount of hits.
   */
  public void setShieldHits(int hits) {
    this.shieldHits = hits;
  }

  /**
   * Handles the command instructions.
   *
   * @param command the command
   * @return true for all commands except quit
   */
  public boolean handleCommand(Command command) {
    Comms comms = new Comms();
    return command.execute(this, comms);
  }

  /**
   * Generates a message based on which robot is in play.
   *
   * @return the message to display to screen.
   */
  public String whatsNext() {
    Colour colour = new Colour();
    Random rand = new Random();

    String[] sniper = {
      "\n<sniper-" + this.getName() + "> It is time to act!\n",
      "\n<sniper-" + this.getName() + "> I quantify skill by the evidence.\n",
      "\n<sniper-" + this.getName() + "> Time to end this.\n",
      "\n<sniper-" + this.getName() + "> I wish for one thing: results\n",
      "\n<sniper-" + this.getName() + "> We have no choice but to win.\n",
      "\n<sniper-" + this.getName() + "> Imagine the worst possible outcome. Now avoid that.\n"
    };

    String[] tanker = {
      "\n<tanker-" + this.getName() + "> Just like in training. Visualize, then execute.\n",
      "\n<tanker-" + this.getName() + "> We all have our jobs to do.\n",
      "\n<tanker-" + this.getName() + "> Make each round count.\n",
      "\n<tanker-" + this.getName() + "> Safety's off, weapon's free.\n",
      "\n<tanker-" + this.getName() + "> Ready up, it’s go time\n",
      "\n<tanker-" + this.getName() + "> We shall prove ourselves in glorious combat!\n"
    };

    String[] bomber = {
      "\n<bomber-" + this.getName() + "> What a bunch of misfits we got here: I love it!\n",
      "\n<bomber-" + this.getName() + "> It's a perfect day for some mayhem.\n",
      "\n<bomber-" + this.getName() + "> So many bombs, so little time.\n",
      "\n<bomber-" + this.getName() + "> It's showtime!\n",
      "\n<bomber-" + this.getName() + "> After this, we should hang out!\n",
      "\n<bomber-" + this.getName() + "> If at first you don't succeed... Blow it up again!\n"
    };

    int index = rand.nextInt(6);

    switch (this.getMake()) {
      case "sniper":
        return (colour.white(sniper[index]) + colour.cyan("v<<<<<< Enter next command."));
      case "tanker":
        return (colour.white(tanker[index]) + colour.cyan("v<<<<<< Enter next command."));
      case "bomber":
        return (colour.white(bomber[index]) + colour.cyan("v<<<<<< Enter next command."));
      default:
        return (colour.white(
            "<T1101> Uh-oh. I shouldn't be here.\n" + "        I'm just the AI not a BATTLE-BOT."));
    }
  }

  @Override
  public String toString() {
    return ">>> >>> >>> " + this.output;
  }
}
