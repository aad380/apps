package org.cs.mazetanks;

import java.awt.*;

public class TextAreaLog extends TextArea implements Log {

    public TextAreaLog() {
        super();
        super.setEditable(false);
    }

    public TextAreaLog(int rows, int cols) {
        super(rows, cols);
        super.setEditable(false);
    }

    public void writeln(String string) {
        appendText(string + "\n");
    }

    public void setEditable(boolean flag) {
        // disable log editing
    }

}

