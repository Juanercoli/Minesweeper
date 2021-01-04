package view;

import exception.IllegalNumberException;
import model.Board;
import model.Cell;
import model.difficulty.Difficulty;
import controller.Game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Represents the frame of the game
 * Created by Juan on ene. 2021.
 */
public class MainFrame extends JFrame {

    private JPanel principalContainerP;
    private JPanel gridContainerP;
    private JButton[][] buttons;

    public Board getBoard() {
        return board;
    }

    private final Board board;

    private int remainingCells;
    private boolean touchedBomb;

    public MainFrame(Difficulty difficulty) {

        board = new Board(difficulty);

        setup();

        setVisible(true);

    }

    private void setFrame() {
        getContentPane().setBackground(Colours.PASTEL_PINK.color());
        setTitle("Minesweeper");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setMinimumSize(new Dimension(screenWidth / 2, screenHeight / 2));
    }

    private void setMainPanel() {

        // Main view
        GridBagLayout principalLayout = new GridBagLayout();
        getContentPane().setLayout(principalLayout);

        // Principal panel
        GridBagLayout layoutPrincipalContainer = new GridBagLayout();
        principalContainerP = new JPanel();
        principalContainerP.setLayout(layoutPrincipalContainer);
        principalContainerP.setBackground(Colours.PASTEL_BLUE.color());

        // Principal ScrollPane
        JScrollPane principalSP = new JScrollPane();
        principalSP.setViewportView(principalContainerP);
        principalSP.setBorder(new LineBorder(Colours.OBSCURE.color(), 5, false));

        // GridBag constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 5, 10, 5);
        getContentPane().add(principalSP, gbc);
    }

    private void setGridPanel() {

        // Grid container panel
        gridContainerP = new JPanel(new GridBagLayout());
        gridContainerP.setBackground(Colours.PASTEL_PINK.color());

        // Border for the panel
        gridContainerP.setBorder(new MatteBorder(5, 5, 5, 5, Colours.OBSCURE.color()));

        // GridBag constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 5, 10, 5);
        principalContainerP.add(gridContainerP, gbc);
    }

    private void setButtons(Board board) {
        Cell[][] matrix = board.getMatrix();

        buttons = new JButton[matrix.length][matrix.length];

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(3, 3, 3, 3);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {

                buttons[i][j] = new JButton(matrix[i][j].toString());

                int finalI = i;
                int finalJ = j;

                buttons[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {

                        if (e.getButton() == MouseEvent.BUTTON1) {
                            try {
                                if (board.getMatrix()[finalI][finalJ].getState() != 2) {
                                    remainingCells -= board.discoverCell(finalI, finalJ, false);
                                    if (board.getMatrix()[finalI][finalJ].isBomb())
                                        touchedBomb = true;
                                }

                            } catch (IllegalNumberException ex) {
                                ex.printStackTrace();
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            try {
                                remainingCells -= board.discoverCell(finalI, finalJ, true);
                            } catch (IllegalNumberException ex) {
                                ex.printStackTrace();
                            }
                        }

                    }
                });
            }
        }

        for (int i = 0; i < matrix.length; i++) {

            for (int j = 0; j < matrix.length; j++) {
                gridContainerP.add(buttons[i][j], gbc);
                gbc.gridx++;
            }

            gbc.gridx = 0;
            gbc.gridy++;
        }

    }

    private void setMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Game");

        JMenuItem jmiNewGame = new JMenuItem("New game");
        jmiNewGame.addActionListener(e -> {
            Game.main(new String[]{});
            if (Game.getDifficulty() != null) {
                dispose();
            }

        });

        JMenuItem jmiExit = new JMenuItem("Exit");
        jmiExit.addActionListener(e -> System.exit(0));

        fileMenu.add(jmiNewGame);
        fileMenu.add(jmiExit);

        menuBar.add(fileMenu);

        super.setJMenuBar(menuBar);

    }

    private void setup() {
        remainingCells = (int) Math.pow(board.getMatrix().length, 2) - board.getMines();

        setFrame();
        setMainPanel();
        setGridPanel();
        setButtons(board);
        setMenuBar();

        pack();
    }

    public void updateUI(Cell[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                buttons[i][j].setText(matrix[i][j].toString());
            }
        }
    }

    public int getRemainingCells() {
        return remainingCells;
    }

    public boolean isTouchedBomb() {
        return touchedBomb;
    }

}
