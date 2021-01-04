package model;

/**
 * Represents the states that the game can take
 * Created by Juan on ene. 2021.
 */
public enum GameStates {

    LOST(0),
    PLAYING(1),
    WON(2);

    private final int state;

    GameStates(int state) {
        this.state = state;
    }

    /**
     * Lets you get the desired state
     * @return the correspondent state instance
     */
    public int state() {
        return state;
    }
}
