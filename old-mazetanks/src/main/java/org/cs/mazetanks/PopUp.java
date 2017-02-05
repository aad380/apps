package org.cs.mazetanks;

import java.awt.*;

public class PopUp extends Frame {

    public PopUp(String message) {
        setLayout(new GridLayout(2, 1));
        // buttons
        add(new Label(message));
        add(new Button("Ok"));
        // show
        pack();
        show();
    }

    public boolean action(Event evt, Object obj) {
        if (evt.target instanceof Button) {
            dispose();
        }
        return true;
    } // action

} // class PopUp

