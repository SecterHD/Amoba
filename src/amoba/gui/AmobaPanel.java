package amoba.gui;

import java.awt.event.ActionListener;
import javax.swing.JPanel;
import amoba.model.AmobaLogic;
import amoba.model.Player;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class AmobaPanel extends JPanel {

    private final AmobaLogic logic;
    private final ActionListener gameButtonActionListener = new GameButtonActionListener();
    private final InformationPanel info;

    //Az AmobaPanel osztály konstruktora, ami vár egy AmobaLogic-ot és egy 
    //InformationPanel-t. Ezek alapján létre tudja hozni játék panelt.
    public AmobaPanel(final AmobaLogic logic, final InformationPanel info) {
        this.logic = logic;
        this.info = info;
        newGame();
    }

    //Ez a metódus a megkapott logika alapján felépíti a játékpanelt
    //az általunk létrehozott GameButton-okból.
    private void setupGamePanel() {
        removeAll();
        int n = logic.getSize();
        setLayout(new GridLayout(n, n));
        for (int row = 0; row < n; ++row) {
            for (int column = 0; column < n; ++column) {
                final JButton btn = new GameButton(row, column);
                btn.setPreferredSize(new Dimension(50, 50));
                btn.addActionListener(gameButtonActionListener);
                add(btn);
            }
        }
    }

    //Ez a metódus frissiti a játéktáblát.
    private void refreshUI() {
        for (Component component : getComponents()) {
            GameButton btn = (GameButton) component;
            int row = btn.getRow();
            int column = btn.getColumn();
            String btnText = "";
            Player fieldPlayer = logic.getFieldPlayer(row, column);
            switch (fieldPlayer) {
                case X:
                    btnText = "X";
                    break;
                case O:
                    btnText = "O";
                    break;
                case NOBODY:
                    btnText = "";
                    break;
            }

            btn.setText(btnText);
        }
    }

    //Ez a metódus új játékot hoz létre az előző táblaméret alapján.
    public final void newGame() {
        setupGamePanel();
        refreshUI();
    }

    //Lépést levezénylő osztály ami implementálja az ActionListener-t.
    private class GameButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            GameButton gameButton = (GameButton) e.getSource();
            int row = gameButton.getRow();
            int column = gameButton.getColumn();
            logic.step(row, column);
            info.setPlayers();
            refreshUI();
            checkEndGameAndDoDelete();
            refreshUI();
        }

    }

    //Ez a metódus megnézi hogy a játék véget ért-e és ha nem,elvégzi a törlést.
    //3 ugyan olyan jel esetén 1-et,4 esetén 2-t töröl.
    private void checkEndGameAndDoDelete() {
        if (logic.findWinner() == Player.X) {
            info.timeStop();
            JOptionPane.showMessageDialog(null, "Congratulation Mr. X! You won the game!", "Grats", JOptionPane.INFORMATION_MESSAGE);
            info.newGame();
            logic.newGame(this.logic.getSize());
            newGame();
            return;
        } else if (logic.findWinner() == Player.O) {
            info.timeStop();
            JOptionPane.showMessageDialog(null, "Congratulation Mr. O! You won the game!", "Grats", JOptionPane.INFORMATION_MESSAGE);
            info.newGame();
            logic.newGame(this.logic.getSize());
            newGame();
            return;
        } else if (logic.checkTie()) {
            info.timeStop();
            JOptionPane.showMessageDialog(null, "Congratulation Mr. Tie! Nobody Won!", "Grats", JOptionPane.INFORMATION_MESSAGE);
            info.newGame();
            logic.newGame(this.logic.getSize());
            newGame();
            return;
        }
        if(logic.getActualPlayer()!=Player.X){
                logic.deleteX();
            }else{
                logic.deleteO();
            }
    }
}
