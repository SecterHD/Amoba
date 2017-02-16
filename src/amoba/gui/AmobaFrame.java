package amoba.gui;

import amoba.model.AmobaLogic;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

public class AmobaFrame extends JFrame {

    private final AmobaLogic logic;
    private final AmobaPanel panel;
    private final JMenuBar menuBar;
    private final InformationPanel info;

    //AmobaFrame konstruktora,ami vár egy AmobaLogic-ot,
    //ami alapján felépíti az ablakot.
    public AmobaFrame(AmobaLogic logic) {
        this.logic = logic;
        setFrameProperties();
        applyNimbusLookAndFeelTheme();
        logic.newGame(6);

        this.info = new InformationPanel(logic);
        getContentPane().add(info, BorderLayout.NORTH);
        this.panel = new AmobaPanel(logic, info);
        getContentPane().add(panel, BorderLayout.CENTER);
        this.menuBar = new AmobaMenuBar();
        setJMenuBar(menuBar);

        pack();
    }

    //Ez a metódus beállítja az ablak címét,méretét,felosztását,iconját,
    //és bezárás esetén megerősítést kér.
    private void setFrameProperties() {
        setTitle("Amoba game");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocation(100, 100);
        setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                showExitConfirmation();
            }

        });
        URL url = AmobaFrame.class.getResource("icon.gif");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));
    }

    //Ez a metódus létrehoz egy felugró ablakot,ahol nem-re kattintva 
    //folytatódik a játék,igenre kattintva meghív egí másik metódust.
    private void showExitConfirmation() {
        int n = JOptionPane.showConfirmDialog(this, "Valóban ki akar lépni?",
                "Megerősítés", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            doUponExit();
        }
    }

    //Ez a metódus ténylegesen bezárja az ablakot.
    protected void doUponExit() {
        this.dispose();
    }

    //Ez a metódus beállítja az ablak témáját.
    private void applyNimbusLookAndFeelTheme() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }
    }

    //AmobaMenuBar osztály létrehozáse.
    private class AmobaMenuBar extends JMenuBar {

        private final JMenu gameMenu;
        private final JMenuItem newGame;
        private final JMenuItem exitGame;

        //AmobaMenuBar konstruktora,ami a legördülő menü létrehozásáért felelős.
        public AmobaMenuBar() {
            gameMenu = new JMenu("Game");
            newGame = new JMenuItem(newGameAction);
            exitGame = new JMenuItem(exitAction);
            newGame.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            exitGame.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            gameMenu.add(newGame);
            gameMenu.add(exitGame);
            add(gameMenu);
        }

        //Ez a metódus új játékot hoz létre,különböző pályaméterekkel.
        private final Action newGameAction = new AbstractAction("New game") {

            @Override
            public void actionPerformed(ActionEvent ae) {
                final Integer[] gameSizes = new Integer[]{6, 10, 14};
                final Object resultObject
                        = JOptionPane.showInputDialog(rootPane, "Select a new game table size",
                                "New game", JOptionPane.QUESTION_MESSAGE, null, gameSizes, gameSizes[0]);
                if (resultObject != null) {
                    int gameSize = (int) resultObject;
                    logic.newGame(gameSize);
                    panel.newGame();
                    info.newGame();
                    pack();
                }
            }

        };

        //Ablak bezárásáért felelős metódus a menün belül.
        private final AbstractAction exitAction = new AbstractAction("Exit") {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
    }
}
