package byow.Core;

public interface interactiveEngine {
    // get the next key to pass into the interactive engine.
    public char getNextKey();
    // test if there exist next key to explore.
    public boolean possibleNextInput();
    // clear an existing world
    public void clearWorld();
    // save the world and current status.
    public void saveWorld();
    // load the saved world and render the status of world
    public void loadWorld();
    //



}
