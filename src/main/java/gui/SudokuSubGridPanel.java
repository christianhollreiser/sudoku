package gui;

import javax.swing.*;
import java.awt.*;

public class SudokuSubGridPanel extends JPanel {

    public SudokuSubGridPanel(){
        int size = 3; // TODO get this size from sudoku puzzle somehow

        // make grid
        LayoutManager layout = new GridLayout(size, 0);
        this.setLayout(layout);
//        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
    }
}
