package model;

import exception.IllegalNumberException;
import model.difficulty.Difficulty;

import java.util.Random;

/**
 * Represents the board of the game
 * Created by Juan on dic. 2020.
 */
public class Board {

    /**
     * Number of mines in the matrix
     */
    private final int mines;

    /**
     * It's the matrix of cells that represents the board
     */
    private final Cell[][] matrix;

    /**
     * Creates a board with a given difficulty
     * @param difficulty difficulty of the game
     */
    public Board(Difficulty difficulty) {
        int size = difficulty.getSIZE();
        mines = difficulty.getMINES();

        this.matrix = new Cell[size][size];

        initializeMatrix();
        calculateMines();
    }

    /**
     *
     * @return the matrix with the all the cells
     */
    public Cell[][] getMatrix() {
        return matrix;
    }

    /**
     * Initializes the matrix with random mines
     */
    private void initializeMatrix() {
        Random random = new Random();

        // We randomize the assignation of mines to each row/column

        for (int i = 0; i < mines; i++) {
            int row = random.nextInt(matrix.length);
            int column = random.nextInt(matrix.length);

            while (this.matrix[row][column] != null) {
                row = random.nextInt(matrix.length);
                column = random.nextInt(matrix.length);
            }

            this.matrix[row][column] = new Cell(0, -1);
        }

        // While there is a cell that isn't a bomb we create a new Cell
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(this.matrix[i][j] == null) {
                    this.matrix[i][j] = new Cell(0, 0);
                }
            }
        }

    }

    /**
     * Prints the matrix (used in the non-graphics demo)
     */
    public void printMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            System.out.print(i + "|");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(" " + this.matrix[i][j] + " ");
            }
            System.out.print("\n");
        }

        System.out.print("  ");

        for (int i = 0; i < matrix.length; i++) {
            System.out.print(" " + "-" + " ");
        }

        System.out.println();

        System.out.print("  ");

        for (int i = 0; i < matrix.length; i++) {
            System.out.print(" " + i + " ");
        }

        System.out.println();
    }

    /**
     * Calculates the quantity of mines around the non-mined cells.
     * The calculation goes from left to right:
     *   --->
     *       - - -
     *       -   -
     *       - - -
     */
    private void calculateMines() {
        boolean isValid;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (this.matrix[i][j].isBomb()) {

                    isValid = validatePosition(i - 1, j - 1);
                    if(isValid && !this.matrix[i - 1][j - 1].isBomb()) {
                        this.matrix[i - 1][j - 1].incValue();
                    }

                    isValid = validatePosition(i - 1, j);
                    if(isValid && !this.matrix[i - 1][j].isBomb()) {
                        this.matrix[i - 1][j].incValue();
                    }

                    isValid = validatePosition(i - 1, j + 1);
                    if(isValid && !this.matrix[i - 1][j + 1].isBomb()) {
                        this.matrix[i - 1][j + 1].incValue();
                    }

                    isValid = validatePosition(i, j - 1);
                    if(isValid && !this.matrix[i][j - 1].isBomb()) {
                        this.matrix[i][j - 1].incValue();
                    }

                    isValid = validatePosition(i, j + 1);
                    if(isValid && !this.matrix[i][j + 1].isBomb()) {
                        this.matrix[i][j + 1].incValue();
                    }

                    isValid = validatePosition(i + 1, j - 1);
                    if(isValid && !this.matrix[i + 1][j - 1].isBomb()) {
                        this.matrix[i + 1][j - 1].incValue();
                    }

                    isValid = validatePosition(i + 1, j);
                    if(isValid && !this.matrix[i + 1][j].isBomb()) {
                        this.matrix[i + 1][j].incValue();
                    }

                    isValid = validatePosition(i + 1, j + 1);
                    if(isValid && !this.matrix[i + 1][j + 1].isBomb()) {
                        this.matrix[i + 1][j + 1].incValue();
                    }

                }
            }
        }
    }

    /**
     * Discovers the cell or adjacent cells if the cell value is 0
     * @param i row of the cell
     * @param j column of the cell
     * @return true if the cell is a bomb
     */
    public int discoverCell(int i, int j, boolean f) throws IllegalNumberException{
        boolean isValid;
        int quantity = 0;

        isValid = validatePosition(i, j);
        if (!isValid)
            throw new IllegalNumberException("That's not a valid number of row/column");

        if (f && this.matrix[i][j].getState() != 1) {
            if(this.matrix[i][j].getState() == 0)
                this.matrix[i][j].setState(2);
            else
                this.matrix[i][j].setState(0);
            return 0;
        }

        if (this.matrix[i][j].getState() == 0) {
            this.matrix[i][j].setState(1);
            quantity++;

            if(this.matrix[i][j].isBomb())
                return 0;

            if (this.matrix[i][j].getValue() == 0) {

                isValid = validatePosition(i - 1, j - 1);
                if(isValid && !this.matrix[i - 1][j - 1].isBomb()) {
                    quantity += discoverCell(i - 1, j - 1, false);
                }

                isValid = validatePosition(i - 1, j);
                if(isValid && !this.matrix[i - 1][j].isBomb()) {
                    quantity += discoverCell(i - 1, j, false);
                }

                isValid = validatePosition(i - 1, j + 1);
                if(isValid && !this.matrix[i - 1][j + 1].isBomb()) {
                    quantity += discoverCell(i - 1, j + 1, false);
                }

                isValid = validatePosition(i, j - 1);
                if(isValid && !this.matrix[i][j - 1].isBomb()) {
                    quantity += discoverCell(i, j - 1, false);
                }

                isValid = validatePosition(i, j + 1);
                if(isValid && !this.matrix[i][j + 1].isBomb()) {
                    quantity += discoverCell(i, j + 1, false);
                }

                isValid = validatePosition(i + 1, j - 1);
                if(isValid && !this.matrix[i + 1][j - 1].isBomb()) {
                    quantity += discoverCell(i + 1, j - 1, false);
                }

                isValid = validatePosition(i + 1, j);
                if(isValid && !this.matrix[i + 1][j].isBomb()) {
                    quantity += discoverCell(i + 1, j, false);
                }

                isValid = validatePosition(i + 1, j + 1);
                if(isValid && !this.matrix[i + 1][j + 1].isBomb()) {
                    quantity += discoverCell(i + 1, j + 1, false);
                }
            }
        }
        return quantity;
    }

    /**
     *
     * @param i row of the cell to validate
     * @param j column of the cell to validate
     * @return validates the position of a coordinate
     */
    private boolean validatePosition(int i, int j) {
        if(i < 0) return false;
        if(i > matrix.length - 1) return false;

        if(j < 0) return false;
        return j <= matrix.length - 1;
    }

    public int getMines() {
        return mines;
    }
}
