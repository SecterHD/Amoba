package amoba.gui;

import javax.swing.JButton;

public class GameButton extends JButton {

    private final int row;
    private final int column;

    //A játékhoz használt gombok konstuktora.2 int-et kér,ami alapján 
    //meghatározza,hogy hol helyezkedik el a játéktáblán.
    public GameButton(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    //Getterek a sor és oszlop lekéréséhez.
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
