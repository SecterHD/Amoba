package amoba.model;

import java.util.ArrayList;
import java.util.Random;

public class AmobaLogic {

    private int size;
    private Player actualPlayer;
    private Player[][] table;
    private ArrayList<int[]> xPositions;
    private ArrayList<int[]> oPositions;

    //Ez a metódus 1 intet vár,ami a mátrix sorait és oszlopait jelöli.
    //Inicializálja az adattagokat és feltölti a Playerekből álló mátrixot
    //NOBODY-kkal.
    public void newGame(final int size) {
        this.size = size;
        actualPlayer = Player.X;
        xPositions = new ArrayList<>();
        oPositions = new ArrayList<>();

        table = new Player[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                table[i][j] = Player.NOBODY;
            }
        }
    }

    //Lépést levezénylő metódus.2 számot vár,a a mátrix sorát és oszlopát.
    //Ha a játéktábl azon mezője ahova klikkeltünk nem üres,
    //akkor új mezőt kell választanunk.
    //Ha jó helyre klikkeltünk,akkor a mező felveszi az aktuális játékost.
    //Az X és O által elfoglalt mezőket ArrayListbe eltároljuk.
    //Végül átállítjuk az aktuális játékost.
    public Player step(int row, int column) {
        if (table[row][column] != Player.NOBODY) {
            return table[row][column];
        }

        table[row][column] = actualPlayer;
        int[] tmp = {row, column};

        if (actualPlayer == Player.X) {
            xPositions.add(tmp);
            actualPlayer = Player.O;

        } else {
            oPositions.add(tmp);
            actualPlayer = Player.X;

        }

        return table[row][column];
    }

    //Ez a metódus megkeresi a győztest.
    //Ha a 4 irányból valamelyiken összegyűlt 5 egymás meletti elem valakinek,
    //akkor visszaadja a győztest.
    public Player findWinner() {
        if (check4WayX(5)) {
            return Player.X;
        } else if (check4WayO(5)) {
            return Player.O;
        }
        return Player.NOBODY;
    }

    //X törlése.
    //Ha 4 gyűlt össze 2-t töröl,ha 3 akkor 1-et.
    public void deleteX() {
        Random r = new Random();
        int random = r.nextInt(xPositions.size());

        if (check4WayX(4)) {
            int[] tmp = xPositions.get(random);
            table[tmp[0]][tmp[1]] = Player.NOBODY;
            xPositions.remove(random);
            random = r.nextInt(xPositions.size());
            tmp = xPositions.get(random);
            table[tmp[0]][tmp[1]] = Player.NOBODY;
            xPositions.remove(random);
        } else if (check4WayX(3)) {
            int[] tmp = xPositions.get(random);
            table[tmp[0]][tmp[1]] = Player.NOBODY;
            xPositions.remove(random);
        }
    }

    //O törlése.
    //Ha 4 gyűlt össze 2-t töröl,ha 3 akkor 1-et.
    public void deleteO() {
        Random r = new Random();
        int random = r.nextInt(oPositions.size());
        if (check4WayO(4)) {
            int[] tmp = oPositions.get(random);
            table[tmp[0]][tmp[1]] = Player.NOBODY;
            oPositions.remove(random);
            random = r.nextInt(oPositions.size());
            tmp = oPositions.get(random);
            table[tmp[0]][tmp[1]] = Player.NOBODY;
            oPositions.remove(random);
        } else if (check4WayO(3)) {
            int[] tmp = oPositions.get(random);
            table[tmp[0]][tmp[1]] = Player.NOBODY;
            oPositions.remove(random);
        }
    }

    //Vízszintesen vizsgálja a mátrix sorait.Azt a játékost adja vissza,
    //akinek összegyűlt a paraméterben megadott számú elem.
    private Player checkHorizontal(int inarow) {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size - (inarow - 1); ++j) {
                if (!(table[i][j] == Player.NOBODY)) {
                    ArrayList<Player> tmp = new ArrayList<>();
                    for (int k = 0; k < inarow; ++k) {
                        tmp.add(table[i][j + k]);
                    }
                    if (checkInARow(tmp) != Player.NOBODY) {
                        return checkInARow(tmp);
                    }
                }
            }
        }
        return Player.NOBODY;
    }

    //Függőlegesen vizsgálja a mátrix oszlopait.Azt a játékost adja vissza,
    //akinek összegyűlt a paraméterben megadott számú elem.
    private Player checkVertical(int inarow) {
        for (int i = 0; i < size - (inarow - 1); ++i) {
            for (int j = 0; j < size; ++j) {
                ArrayList<Player> tmp = new ArrayList<>();
                if (!(table[i][j] == Player.NOBODY)) {
                    for (int k = 0; k < inarow; ++k) {
                        tmp.add(table[i + k][j]);
                    }
                    if (checkInARow(tmp) != Player.NOBODY) {
                        return checkInARow(tmp);
                    }
                }
            }
        }
        return Player.NOBODY;
    }

    //Átlósan,északnyugatról délkeletre vizsgálja a mátrixot.Azt a játékost adja vissza,
    //akinek összegyűlt a paraméterben megadott számú elem.
    private Player checkDiagonalNWtoSE(int inarow) {
        for (int i = 0; i < size - (inarow - 1); ++i) {
            for (int j = 0; j < size - (inarow - 1); ++j) {
                ArrayList<Player> tmp = new ArrayList<>();
                if (!(table[i][j] == Player.NOBODY)) {
                    for (int i2 = i, j2 = j; i2 <= i + (inarow - 1) && j2 <= j + (inarow - 1); ++i2, ++j2) {
                        tmp.add(table[i2][j2]);
                    }
                    if (checkInARow(tmp) != Player.NOBODY) {
                        return checkInARow(tmp);
                    }
                }
            }
        }
        return Player.NOBODY;
    }

    //Átlósan,délnyugatról északkeletre vizsgálja a mátriot.Azt a játékost adja vissza,
    //akinek összegyűlt a paraméterben megadott számú elem.
    private Player checkDiagonalSWtoNE(int inarow) {
        for (int i = 0; i < size - (inarow - 1); ++i) {
            for (int j = (inarow - 1); j < size; ++j) {
                ArrayList<Player> tmp = new ArrayList<>();
                if (!(table[i][j] == Player.NOBODY)) {
                    for (int i2 = i, j2 = j; i2 <= i + (inarow - 1) && j2 >= j - (inarow - 1); ++i2, --j2) {
                        tmp.add(table[i2][j2]);
                    }
                    if (checkInARow(tmp) != Player.NOBODY) {
                        return checkInARow(tmp);
                    }
                }
            }
        }
        return Player.NOBODY;
    }

    //Kiemelt segédfüggvény kódismétlés elkerülése végett.
    //Megkapja a listánkat,amibe megnézi hogy minden elem X-e vagy O-e.
    private Player checkInARow(ArrayList<Player> tmp) {
        if (tmp.contains(Player.X) && !tmp.contains(Player.O) && !tmp.contains(Player.NOBODY)) {
            return Player.X;
        } else if (!tmp.contains(Player.X) && tmp.contains(Player.O) && !tmp.contains(Player.NOBODY)) {
            return Player.O;
        }
        return Player.NOBODY;
    }

    //2db kiemelt segédfüggvény kódismétlés elkerülése végett.
    //Megnézi hogy a 4 irány valamelyikébe összegyűlt-e a paraméterként kapott
    //számú eleme az X-nek vagy az O-nak.
    private boolean check4WayX(int inarow) {
        return checkHorizontal(inarow) == Player.X
                || checkVertical(inarow) == Player.X
                || checkDiagonalNWtoSE(inarow) == Player.X
                || checkDiagonalSWtoNE(inarow) == Player.X;
    }

    private boolean check4WayO(int inarow) {
        return checkHorizontal(inarow) == Player.O
                || checkVertical(inarow) == Player.O
                || checkDiagonalNWtoSE(inarow) == Player.O
                || checkDiagonalSWtoNE(inarow) == Player.O;
    }

    //3db Getter függvény privát adattagok eléréséhez.
    public int getSize() {
        return size;
    }

    public Player getActualPlayer() {
        return actualPlayer;
    }

    public Player getFieldPlayer(int i, int j) {
        return table[i][j];
    }

    //Ez a metódus ellenörzi hogy megtelt-e a pálya,esetleges döntetlen miatt.
    public boolean checkTie() {
        boolean l = true;
        for (int i = 0; i < size && l; ++i) {
            for (int j = 0; j < size && l; ++j) {
                l = table[i][j] != Player.NOBODY;
            }
        }
        return l;
    }

}
