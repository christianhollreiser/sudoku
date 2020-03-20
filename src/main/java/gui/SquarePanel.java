package gui;

import javax.swing.*;
import java.awt.*;

public class SquarePanel extends JPanel {

    public SquarePanel() {
        LayoutManager layout = new GridLayout(2,0);
        this.setLayout(layout);
        this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

    }
}
