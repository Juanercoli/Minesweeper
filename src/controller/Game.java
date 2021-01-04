package controller;

import model.GameStates;
import model.difficulty.Difficulty;
import view.MainFrame;
import view.SettingsPane;

import javax.swing.*;

/**
 * Represents the game
 * Created by Juan on ene. 2021.
 */
public class Game {

    private static Difficulty difficulty;

    /**
     * Runs the game
     * @param args *deprecated*
     */
    public static void main(String[] args) {
        setup();
    }

    /**
     * Setups the start of the game
     */
    private static void setup() {

        // First we show the player an options pane with the settings of difficulty
        SettingsPane settingsPane = new SettingsPane();
        difficulty = settingsPane.getDifficulty();

        // We use the difficulty value to close/open frames of the game with an old/new difficulty
        if (difficulty != null) {
            startThread();
        }
    }

    /**
     * Starts the thread of the game
     */
    private static void startThread() {
        Thread thread = new Thread(new GameTask(difficulty));
        thread.start();
    }

    /**
     *
     * @return difficulty of the game
     */
    public static Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Represents a the game task, that will be run by a Thread
     */
    private static class GameTask implements Runnable {

        /**
         * Mainframe of the game
         */
        private final MainFrame mainFrame;

        /**
         * If the gameState == 0 --> Last the game
         * If the gameState == 1 --> Playing the game
         * If the gameState == 2 --> Won the game
         */
        private int gameState = GameStates.PLAYING.state();

        /**
         * Creates the mainframe of the game
         * @param difficulty the difficulty of the game
         */
        public GameTask(Difficulty difficulty) {
            mainFrame = new MainFrame(difficulty);
        }

        @Override
        public void run() {

            // This variable is used to count the remaining cells
            // with remaining cells i mean "good" remaining cells
            int remainingCells;

            // This means while the user is playing the game...
            while (gameState == GameStates.PLAYING.state()) {

                // We'll ask for the remaining cells to the frame
                remainingCells = mainFrame.getRemainingCells();

                // If remaining cells equals to 2 --> you won the game!
                if (remainingCells == 0) {
                    gameState = GameStates.WON.state();
                }

                // If you touched a bomb --> you lose :(
                if (mainFrame.isTouchedBomb()) {
                    gameState = GameStates.LOST.state();
                }

                // After an iteration we must update the UI
                mainFrame.updateUI(mainFrame.getBoard().getMatrix());

            }

            // If we are here it means that the game has changed his state

            if (gameState == GameStates.LOST.state()) {
                // If you lost you should be here
                JOptionPane.showMessageDialog(mainFrame,
                        "You lost, try again!!");
            } else {
                // If you won you should be here
                JOptionPane.showMessageDialog(mainFrame,
                        "You won, congratulations!!");
            }
        }

    }
}
