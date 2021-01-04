package view;

import model.difficulty.Difficulty;
import model.difficulty.Easy;
import model.difficulty.Hard;
import model.difficulty.Medium;

import javax.swing.*;

/**
 * This represents the settings panel
 * Created by Juan on ene. 2021.
 */
public class SettingsPane {

    private final Difficulty difficulty;

    public SettingsPane() {
        Icon errorIcon = UIManager.getIcon("OptionPane.errorIcon");
        Difficulty[] possibilities = {new Easy(), new Medium(), new Hard()};

        difficulty = (Difficulty) JOptionPane.showInputDialog(null,
                "Select difficulty:", "Minesweeper",
                JOptionPane.PLAIN_MESSAGE, errorIcon, possibilities, new Easy());
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
