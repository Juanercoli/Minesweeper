package model.difficulty;

/**
 * Created by Juan on ene. 2021.
 */
public class Difficulty {

    private final int SIZE;
    private final int MINES;

    public Difficulty(int size, int mines) {
        this.SIZE = size;
        this.MINES = mines;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public int getSIZE() {
        return SIZE;
    }

    public int getMINES() {
        return MINES;
    }
}
