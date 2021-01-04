import exception.IllegalNumberException;
import model.Board;
import model.difficulty.Medium;

import java.io.IOException;
import java.util.Scanner;

/**
 * Implementation of minesweeper without using graphics
 * NOTE: It's recommended to play with a board with a size of 10 or less
 */
public class GameDemoNG {
    public static void main(String[] args) {
        Board board = new Board(new Medium());
        Scanner scanner = new Scanner(System.in);
        final int MAX_MINES = board.getMines();
        int remainingCells = (int) (Math.pow(board.getMatrix().length, 2) - board.getMines());

        /*
         * If the gameState == 0 --> Last the game
         * If the gameState == 1 --> Playing the game
         * If the gameState == 2 --> Won the game
         * */
        int gameState = 1;

        do {
            System.out.println("MAX MINES: " + MAX_MINES);
            System.out.println("REMAINING CELLS: " + remainingCells);
            board.printMatrix();
            System.out.println("Write a coordinate (row :: column :: flag) to discover a cell:");

            while (!scanner.hasNextInt()) {
                System.out.println("Format must be number :: number :: boolean");
                scanner.next();
            }
            int i = scanner.nextInt();

            while (!scanner.hasNextInt()) {
                System.out.println("Format must be number :: number :: boolean");
                scanner.next();
            }
            int j = scanner.nextInt();

            while (!scanner.hasNextBoolean()) {
                System.out.println("Format must be number :: number :: boolean");
                scanner.next();
            }

            boolean f = scanner.nextBoolean();

            try {
                remainingCells -= board.discoverCell(i, j, f);

                // verify if the cell is a bomb --> lost the game
                if (board.getMatrix()[i][j].isBomb()) {
                    gameState = 0;
                }

                // Verify if all cells are visible --> won the game
                if (remainingCells == 0) {
                    gameState = 2;
                }
            } catch (IllegalNumberException e) {
                e.printStackTrace();
            }

            clearConsole();

        } while (gameState == 1);

        System.out.println("MAX MINES: " + MAX_MINES);
        System.out.println("REMAINING CELLS: " + remainingCells);
        board.printMatrix();

        if (gameState == 0) {
            System.out.println("You lost, try again!");
        } else {
            System.out.println("You won, congratulations!");
        }

    }

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
