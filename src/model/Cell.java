package model;

/**
 * Represents a cell on the board
 * Created by Juan on dic. 2020.
 */
public class Cell {

    /**
     * Indicates the state of the cell
     * 0: it's hidden
     * 1: it's visible
     * 2: it's flagged
     */
    private int state;

    /**
     * Indicates the value of the cell
     * if value == -1 the cell is a bomb
     */
    private int value;

    /**
     *
     * @param state indicates the state of the cell
     * @param value indicates the value of the cell
     */
    public Cell(int state, int value) throws IllegalStateException {
        if (state < 0 || state > 2) {
            throw new IllegalStateException("The state of the cell must be a number like 0, 1 or 2.");
        }
        else {
            this.state = state;
        }

        this.value = value;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isBomb() {
        return this.value == -1;
    }

    public void incValue(){
        this.value++;
    }

    @Override
    public String toString() {
        if (this.state == 0) return "■";
        else
            if (this.state == 2) return "F";
            else
                if (this.state == 1 && this.value == -1) return "*";
        return String.valueOf(this.value);
    }
}
