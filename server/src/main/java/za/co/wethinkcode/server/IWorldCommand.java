package za.co.wethinkcode.server;

/**
 * The interface World command.
 */
public interface IWorldCommand {

    /**
     * Execute string.
     *
     * @param world the world
     * @return the string
     */
    String execute(World world);
}
