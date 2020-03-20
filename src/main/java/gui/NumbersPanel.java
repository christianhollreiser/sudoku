package gui;

import javax.swing.*;
import java.awt.*;

public class NumbersPanel extends JPanel {

    public NumbersPanel(){
        // make grid
        LayoutManager layout = new GridLayout(6,0);
        this.setLayout(layout);
        this.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

    }
}
