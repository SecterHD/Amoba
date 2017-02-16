package amoba.gui;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import amoba.model.AmobaLogic;

public class InformationPanel extends JPanel {

    private final JLabel elapsedTimeTextLabel;
    private final JLabel elapsedTimeLabel;
    private final JLabel actualPlayerTextLabel;
    private final JLabel actualPlayerLabel;

    private final Font textFont;

    private Timer elapsedTime;

    private final AmobaLogic logic;

//    A InformacionPanel osztály konstruktora. Létrehoz négy darab JLabelt,
//    amiből kettő állandó szöveg, a másik kettőt meg az adott játékos illetve
//    az eltelt idő alapján frissül.
    public InformationPanel(AmobaLogic logic) {
        setPreferredSize(new Dimension(100, 50));
        textFont = new Font("Garamond", Font.HANGING_BASELINE, 16);

        elapsedTimeTextLabel = new JLabel("Elapsed time: ");
        elapsedTimeTextLabel.setFont(textFont);

        elapsedTimeLabel = new JLabel("");
        elapsedTimeLabel.setFont(textFont);

        actualPlayerTextLabel = new JLabel("Actual Player: ");
        actualPlayerTextLabel.setFont(textFont);

        actualPlayerLabel = new JLabel("");
        actualPlayerLabel.setFont(textFont);

        this.logic = logic;

        add(elapsedTimeTextLabel);
        add(elapsedTimeLabel);
        add(new JLabel("    "));
        add(actualPlayerTextLabel);
        add(actualPlayerLabel);

        newGame();
    }

    //Ez a metódus új játék esetén a számlálót 00:00:00-ra állítja,
    //az aktuális játékost pedig X-re.
    public final void newGame() {
        if (elapsedTime != null) {
            elapsedTime.stop();
        }
        elapsedTimeLabel.setText("00:00:00");
        setPlayers();
        elapsedTime = new Timer(1000, new TimerAction(elapsedTimeLabel));
        elapsedTime.start();
    }

    //Ez a metódus állítja át az aktuális játékost a soron következőre.
    public void setPlayers() {
        actualPlayerLabel.setText(logic.getActualPlayer().name());
    }

    //Ez a metódus megállítja a számlálót.
    public void timeStop() {
        elapsedTime.stop();
    }
}
