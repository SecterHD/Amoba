package amoba.gui;

import java.awt.event.ActionEvent;
import java.time.Duration;
import java.time.Instant;
import javax.swing.AbstractAction;
import javax.swing.JLabel;

public class TimerAction extends AbstractAction {

    private final JLabel timerLabel;
    private final Instant startTime;

    //A TimerAction osztály konstruktora, ami vár egy JLabelt vár paraméterül,
    //amit frissíteni tud az aktuális idővel.
    public TimerAction(final JLabel timerLabel) {
        this.timerLabel = timerLabel;
        this.startTime = Instant.now();
    }

    //Ez a metódus frissíti a konstruktorban megadott JLabel feliratát.
    @Override
    public void actionPerformed(ActionEvent ae) {
        timerLabel.setText(formatDuration(Duration.between(startTime, Instant.now())));
    }

    //Ez a metódus visszaadja String formátumban másodpercre, percre és órára
    //lebontva az eltelt időt.
    private String formatDuration(final Duration duration) {
        final long seconds = duration.getSeconds();
        return String.format("%02d:%02d:%02d", seconds / 3600,
                (seconds % 3600) / 60, seconds % 60);
    }
}
