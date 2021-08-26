package za.co.wethinkcode.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorldCommandTest {

    World world = new World(200, 400, 10, 7, 5, 9, 3);

      @Test
      void executeHelp() {
        assertEquals(
            "| See Wiki for detailed info"
                + " ----------------------------------------------------------------------|\n"
                + "| https://github.com/wtc-cohort-2020/0029-robot-worlds/wiki"
                + " ---------------------------------------|\n"
                +
     "|--------------------------------------------------------------------------------------------------|\n"
                + "|----------------------------------- World Command List"
                + " -------------------------------------------|\n"
                +
     "|--------------------------------------------------------------------------------------------------|\n"
                + ": [HELP]     displays what you're reading right now\n"
                + ": [QUIT]     ends the world\n"
                + ": [ROBOTS]   exposes the names and states of the robots\n"
                + ": [PURGE]    deports the named robot e.g. 'PURGE titibot9000' will remove titibot9000 from the world\n"
                + ": [DUMP]     displays the state of the world\n"
                +
     "|--------------------------------------------------------------------------------------------------|\n"
                +
     "|--------------------------------------------------------------------------------------------------|\n",
            new HelpCommandI().execute(world));
      }

      @Test
      void executeQuit() {
        assertEquals("(((Quitting)))...BattleWorld Apocalypse...(((gnittiuQ)))", new QuitCommandI().execute(world));
      }

    @Test
    void executeRobots() {
        RequestObject requestObject = new RequestObject("{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
        Command.create(requestObject).execute(world);
        String state = world.getRobot("hal").getState().toString();
        assertEquals("(((Robots)))\n{hal=" + state + "}\n(((stoboR)))", new RobotsCommandI().execute(world));
    }

    @Test
    void executePurge() {
        assertEquals("(((No purge))) purge command should be followed by a valid robot's name (((egruP oN)))", new PurgeCommandI("").execute(world));
        assertEquals("(((No purge))) purge command should be followed by a valid robot's name (((egruP oN)))", new PurgeCommandI("lah").execute(world));
        RequestObject requestObject = new RequestObject("{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
        Command.create(requestObject).execute(world);
        PurgeCommandI purge = new PurgeCommandI("hal");
        assertEquals("(((Purged))) hal has been scrapped (((degruP)))", purge.execute(world));
    }
}
