package gui;

import generator.*;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SudokuFrame extends JFrame {
    private static final String TITLE = "Sudoku Puzzle";

    private final int FRAME_WIDTH;
    private final int FRAME_HEIGHT;

    private JButton[][] gridButtons;
    private Map<JButton, Position> gridButton_position;
    private JLabel label;

    SudokuPuzzle puzzle;

    private JButton selectedPosition = null;

    public SudokuFrame(SudokuPuzzle puzzle) throws CloneNotSupportedException {
        this.puzzle = puzzle;
        gridButtons = new JButton[this.puzzle.getSize()][this.puzzle.getSize()];
        this.gridButton_position = new HashMap<>();
        this.createComponents();
        this.setLayout(new GridBagLayout());
        this.FRAME_WIDTH = 1100;
        this.FRAME_HEIGHT = 1100; // TODO: change from hardcoded to depending on size of sudoku puzzle
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }



    private void createComponents(){
        JPanel gridPanel = new SudokuGridPanel();
        for (int gridRow = 0; gridRow < (int) puzzle.getSize()/3; gridRow++) {
            for (int gridCol = 0; gridCol < (int) puzzle.getSize()/3; gridCol++){
                JPanel subGridPanel = new SudokuSubGridPanel();
                for (int subGridrow = 0; subGridrow < (int) puzzle.getSize()/3; subGridrow++) {
                    for (int subGridCol = 0; subGridCol < (int) puzzle.getSize()/3; subGridCol++) {
                        int row = gridRow*3 + subGridrow;
                        int col = gridCol*3 + subGridCol;
                        JButton squareButton = new JButton();
                        squareButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                        // add action listener to highlight grid square button when clicked and unhighlight previous one.
                        squareButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (selectedPosition != null) {
                                    setButtonColor(selectedPosition);
                                }
                                selectedPosition = squareButton;
                                selectedPosition.setBackground(new Color(255, 255, 153));
                                selectedPosition.setOpaque(true);

                            }
                        });
                        squareButton.setPreferredSize(new Dimension(75, 75));
                        gridButtons[row][col] = squareButton;
                        Position pos = new Position(row, col);
                        gridButton_position.put(squareButton, pos);
                        subGridPanel.add(squareButton);
                    }
                }
                gridPanel.add(subGridPanel);
            }
        }
        this.add(gridPanel);

        JPanel npanel = new NumbersPanel();
        for(int i = 0; i < puzzle.getSize(); i ++){
            JButton squareButton = new JButton(String.valueOf(i+1));
            squareButton.getInputMap(squareButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(String.valueOf(i+1)), "click_value");
            squareButton.getActionMap().put("click_value", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedPosition != null){
                        setValue(selectedPosition, squareButton.getText());
                    }
                }
            });

            squareButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedPosition != null){
                        setValue(selectedPosition, squareButton.getText());
                    }
                }
            });
            squareButton.setPreferredSize(new Dimension(40,40));
            npanel.add(squareButton);
        }

        JButton delButton = new JButton("\u232b");
        delButton.getInputMap(delButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("BACK_SPACE"), "click_value");
        delButton.getActionMap().put("click_value", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedPosition != null){
                    setValue(selectedPosition, "");
                }
            }
        });

        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedPosition != null){
                    setValue(selectedPosition, "");
                }
            }
        });
        npanel.add(delButton);


        JButton solve = new JButton("solve");
        solve.setOpaque(true);
        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean solved = puzzle.solve();
                if(!solved){
                    solve.setBackground(Color.RED);
                } else {
                    solve.setBackground(Color.GREEN);
                }
            }
        });
        npanel.add(solve);

        JButton newPuzzle = new JButton("replay"); // TODO see what too call this button whether new puzzle or refresh original puzzle
        newPuzzle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    puzzle.newPuzzle();
                } catch (CloneNotSupportedException ex) {
                    ex.printStackTrace();
                }
                solve.setBackground(null);
            }
        });
        npanel.add(newPuzzle);

        this.add(npanel);
    }


    public void setButtonValue(int row, int col, int value){
        String val = ((value == 0) ? "" : String.valueOf(value));
        gridButtons[row][col].setText(val);
        setButtonColor(gridButtons[row][col]);
    }

    public void setButtonColor(JButton button){
        if(!button.getText().equals("")){
            button.setBackground(new Color(204, 222, 255));
            button.setOpaque(true);
        } else {
            button.setBackground(null);
        }
    }

    public void setValue(JButton button, String value){
        int val = ((value.equals("")) ? 0 : Integer.parseInt(value));
        Position pos = gridButton_position.get(button);
        puzzle.setSafeEntry(pos.row, pos.col, val);
    }
}
